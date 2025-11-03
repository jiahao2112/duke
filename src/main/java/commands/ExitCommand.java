package commands;

import exceptions.FileException;
import gui.GrootGUI;
import manager.FileManager;
import tasks.Task;

import java.util.ArrayList;

/**
 * Used to end the program
 */
public class ExitCommand extends Command {

    /**
     * Populate parameter
     *
     * @param tasklist task list to be saved to tasklist file
     * @throws FileException if there are any errors in saving the tasklist
     */
    protected ExitCommand(ArrayList<Task> tasklist) throws FileException {
        super(tasklist);

        FileManager.saveFile(tasklist); //ensure tasklist file updated before exit
    }

    /**
     * Execution of command.
     * Save tasklist to file and end program
     *
     */
    @Override
    public String execute() {
        return GrootGUI.exit();
//        UserInteraction.exit();
    }
}
