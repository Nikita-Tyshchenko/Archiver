package command;


import logic.ConsoleHelper;
import logic.ZipFileManager;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class ZipCommand implements Command{

    protected ZipFileManager getZipFileManager() throws Exception {
        ConsoleHelper.writeMessageToConsole("Введите полный путь файла архива:");
        Path zipPath = Paths.get(ConsoleHelper.readStringFromConsole());
        return new ZipFileManager(zipPath);
    }
}
