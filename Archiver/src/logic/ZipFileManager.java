package logic;

import exception.PathIsNotFoundException;
import exception.WrongZipFileException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipFileManager {

    private final Path zipFile;

    public ZipFileManager(Path zipFile) {
        this.zipFile = zipFile;
    }

    public void removeFileFromArchive(Path path) throws Exception{
        removeFiles(Collections.singletonList(path));
    }


    public void addFileToArchive(Path absolutePath) throws Exception{
        addFiles(Collections.singletonList(absolutePath));
    }

    public void extractFilesFromArchive(Path outputFolder) throws Exception{
        extractAll(outputFolder);
    }

    public List<FileProperties> getListOfFilesFromArchive() throws Exception{
        return getFilesList();
    }

    public void createsZipFromSource(Path source) throws Exception{
        createZip(source);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void createZip(Path source) throws Exception {
        Path zipDirectory = zipFile.getParent();

        if (Files.notExists(zipDirectory)) {
            Files.createDirectories(zipDirectory);
        }

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFile))) {

            if (Files.isDirectory(source)) {
                FileManager fileManager = new FileManager(source);
                List<Path> fileNames = fileManager.getFileList();
                for (Path fileName : fileNames) {
                    addNewZipEntry(zipOutputStream, source, fileName);
                }
            } else if (Files.isRegularFile(source)) {
                addNewZipEntry(zipOutputStream, source.getParent(), source.getFileName());
            } else {
                throw new PathIsNotFoundException();
            }
        }
    }

    private List<FileProperties> getFilesList() throws Exception {
        if (!Files.isRegularFile(zipFile)) {
            throw new WrongZipFileException();
        }

        List<FileProperties> files = new ArrayList<>();

        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))) {

            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                copyData(zipInputStream, byteArrayOutputStream);

                FileProperties file = new FileProperties(zipEntry.getName(), zipEntry.getSize(), zipEntry.getCompressedSize(), zipEntry.getMethod());
                files.add(file);

                zipEntry = zipInputStream.getNextEntry();
            }
        }
        return files;
    }

    private void extractAll(Path outputFolder) throws Exception {
        if (!Files.isRegularFile(zipFile)) {
            throw new WrongZipFileException();
        }

        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))) {

            if (Files.notExists(outputFolder)) {
                Files.createDirectories(outputFolder);
            }

            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {

                Path filePath = outputFolder.resolve(zipEntry.getName());

                if (Files.notExists(filePath.getParent())) {
                    Files.createDirectories(filePath.getParent());
                }
                try (OutputStream outputStream = Files.newOutputStream(filePath)) {
                    copyData(zipInputStream, outputStream);
                }
                zipEntry = zipInputStream.getNextEntry();
            }
        }
    }

    private void removeFiles(List<Path> pathList) throws Exception{
        if(!Files.isRegularFile(zipFile)){
            throw new WrongZipFileException();
        }

        Path tempFile = Files.createTempFile(null, null);

        try(ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile));
            ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(tempFile))){

            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while(zipEntry != null){
                Path filePath = Paths.get(zipEntry.getName());
                if(!pathList.contains(filePath)){
                    zipOutputStream.putNextEntry(new ZipEntry(zipEntry.getName()));
                    copyData(zipInputStream, zipOutputStream);
                } else {
                    ConsoleHelper.writeMessageToConsole(String.format("Файл %s был удален из архива.", filePath));
                }
                zipEntry = zipInputStream.getNextEntry();
            }
            Files.move(tempFile, zipFile, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private void addFiles(List<Path> absolutePathList) throws Exception{
        if(!Files.isRegularFile(zipFile)){
            throw new WrongZipFileException();
        }

        Path tempFile = Files.createTempFile(null, null);
        List<Path> existingFiles = new ArrayList<>();

        try(ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile));
            ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(tempFile))){

            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while(zipEntry != null){
                String fileName = zipEntry.getName();
                zipOutputStream.putNextEntry(new ZipEntry(zipEntry.getName()));
                copyData(zipInputStream, zipOutputStream);
                existingFiles.add(Paths.get(fileName));
                zipEntry = zipInputStream.getNextEntry();
            }

            for(Path path : absolutePathList){
                if(Files.notExists(path) || !Files.isRegularFile(path)){
                    throw new PathIsNotFoundException();
                }
                Path name = path.getFileName();
                if(existingFiles.contains(name)){
                    ConsoleHelper.writeMessageToConsole((String.format("%s уже есть в архиве.", name.getFileName().toString())));
                } else {
                    addNewZipEntry(zipOutputStream, path.getParent(), path.getFileName());
                }
            }
            Files.move(tempFile, zipFile, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private void addNewZipEntry(ZipOutputStream zipOutputStream, Path filePath, Path fileName) throws Exception {
        Path fullPath = filePath.resolve(fileName);
        try (InputStream inputStream = Files.newInputStream(fullPath)) {
            ZipEntry entry = new ZipEntry(fileName.toString());
            zipOutputStream.putNextEntry(entry);
            copyData(inputStream, zipOutputStream);
            zipOutputStream.closeEntry();
        }
    }

    private void copyData(InputStream in, OutputStream out) throws Exception {
        byte[] buffer = new byte[8 * 1024];
        int count;
        while ((count = in.read(buffer)) > 0) {
            out.write(buffer, 0, count);
        }
    }
}
