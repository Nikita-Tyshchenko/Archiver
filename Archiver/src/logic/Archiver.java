package logic;

import exception.WrongZipFileException;

import java.io.IOException;

public class Archiver {
    public static void main(String[] args) throws IOException {

        Operation operation = null;
        do {
            try {
                operation = askOperation();
                CommandExecutor.execute(operation);
            } catch (WrongZipFileException e) {
                ConsoleHelper.writeMessageToConsole("Вы не выбрали файл архива или выбрали неверный файл.");
            } catch (Exception e) {
                ConsoleHelper.writeMessageToConsole("Произошла ошибка. Проверьте введенные данные.");
            }

        } while (operation != Operation.EXIT);
    }

    /**
     * @return Operation user selected to execute
     * @throws IOException
     */
    private static Operation askOperation() throws IOException {

        String string = String.format("%d - упаковать файл в архив\n" +
                        "%d - добавить файл в архив\n" +
                        "%d - удалить файл из архива\n" +
                        "%d - распаковать архив\n" +
                        "%d - посмотреть содрежимое архива\n" +
                        "%d - выход",
                Operation.CREATE.ordinal(), Operation.ADD.ordinal(), Operation.REMOVE.ordinal(),
                Operation.EXTRACT.ordinal(), Operation.CONTENT.ordinal(), Operation.EXIT.ordinal());

        ConsoleHelper.writeMessageToConsole(string);

        return Operation.values()[ConsoleHelper.readIntFromConsole()];
    }
}
