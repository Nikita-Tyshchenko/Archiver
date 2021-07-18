package command;

import exception.PathIsNotFoundException;
import logic.ConsoleHelper;
import logic.ZipFileManager;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipCreateCommand extends ZipCommand{
    @Override
    public void execute() throws Exception {
        try {
            ConsoleHelper.writeMessageToConsole("Создание архива.");

            ZipFileManager zipFileManager = getZipFileManager();

            ConsoleHelper.writeMessageToConsole("Введите полное имя файла или директории для архивации:");
            Path sourcePath = Paths.get(ConsoleHelper.readStringFromConsole());
            zipFileManager.createsZipFromSource(sourcePath);

            ConsoleHelper.writeMessageToConsole("Архив создан.");

        } catch (PathIsNotFoundException e) {
            ConsoleHelper.writeMessageToConsole("Вы неверно указали имя файла или директории.");
        }
    }
}
