package command;

import exception.PathIsNotFoundException;
import logic.ConsoleHelper;
import logic.ZipFileManager;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipExtractCommand extends ZipCommand{
    @Override
    public void execute() throws Exception {
        try {
            ConsoleHelper.writeMessageToConsole("Распаковка архива.");

            ZipFileManager zipFileManager = getZipFileManager();

            ConsoleHelper.writeMessageToConsole("Введите путь для распаковки:");
            Path destinationPath = Paths.get(ConsoleHelper.readStringFromConsole());
            zipFileManager.extractFilesFromArchive(destinationPath);

            ConsoleHelper.writeMessageToConsole("Архив был распакован.");

        } catch (PathIsNotFoundException e) {
            ConsoleHelper.writeMessageToConsole("Неверный путь для распаковки.");
        }
    }
}
