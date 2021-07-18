package command;

import logic.ConsoleHelper;

public class ExitCommand implements Command{
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessageToConsole("До встречи!");
    }
}
