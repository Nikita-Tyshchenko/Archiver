package command;

import logic.ConsoleHelper;
import logic.ZipFileManager;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipRemoveCommand extends ZipCommand{
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessageToConsole("Удаление файла из архива.");

        ZipFileManager zipFileManager = getZipFileManager();

        ConsoleHelper.writeMessageToConsole("Введите полный путь файла в архиве:");
        Path sourcePath = Paths.get(ConsoleHelper.readStringFromConsole());
        zipFileManager.removeFileFromArchive(sourcePath);

        ConsoleHelper.writeMessageToConsole("Удаление из архива завершено.");
    }
}
