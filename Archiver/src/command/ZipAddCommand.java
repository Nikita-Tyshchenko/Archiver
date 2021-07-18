package command;

import exception.PathIsNotFoundException;
import logic.ConsoleHelper;
import logic.ZipFileManager;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipAddCommand extends ZipCommand{
    @Override
    public void execute() throws Exception {
        try {
            ConsoleHelper.writeMessageToConsole("Добавление нового файла в архив.");

            ZipFileManager zipFileManager = getZipFileManager();

            ConsoleHelper.writeMessageToConsole("Введите полное имя файла для добавления:");
            Path sourcePath = Paths.get(ConsoleHelper.readStringFromConsole());
            zipFileManager.addFileToArchive(sourcePath);

            ConsoleHelper.writeMessageToConsole("Добавление в архив завершено.");

        } catch (PathIsNotFoundException e) {
            ConsoleHelper.writeMessageToConsole("Файл не был найден.");
        }
    }
}
