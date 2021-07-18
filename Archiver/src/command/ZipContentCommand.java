package command;

import logic.ConsoleHelper;
import logic.FileProperties;
import logic.ZipFileManager;

import java.util.List;

public class ZipContentCommand extends ZipCommand{
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessageToConsole("Просмотр содержимого архива.");

        ZipFileManager zipFileManager = getZipFileManager();

        ConsoleHelper.writeMessageToConsole("Содержимое архива:");

        List<FileProperties> files = zipFileManager.getListOfFilesFromArchive();
        for (FileProperties file : files) {
            ConsoleHelper.writeMessageToConsole(file.toString());
        }

        ConsoleHelper.writeMessageToConsole("Содержимое архива прочитано.");
    }
}
