package commands;

import enums.CommandType;
import exceptions.FileException;
import manager.FileManager;
import tasks.Task;
import ui.UserInteraction;

import java.util.AbstractMap;
import java.util.ArrayList;

public class ExitCommand extends Command {

    protected ExitCommand(ArrayList<Task> tasklist) {
        super(tasklist);
    }

    @Override
    public void execute() throws FileException {
        FileManager.saveFile(tasklist); //ensure tasklist file updated before exit
        UserInteraction.exit();
    }
}
