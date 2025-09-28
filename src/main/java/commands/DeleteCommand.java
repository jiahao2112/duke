package commands;

import enums.CommandType;
import exceptions.MarkUnmarkDeleteException;
import parser.userInputParser.MarkUnmarkDeleteParser;
import tasks.Task;
import ui.UserInteraction;

import java.util.AbstractMap;
import java.util.ArrayList;

/**
 * Used for deletion of task from task list
 */
public class DeleteCommand extends Command {
    private final int taskNumber;

    /**
     * Populate parameter required
     *
     * @param commandLine parsed user's input
     * @param tasklist    task list from task manager
     * @throws MarkUnmarkDeleteException if there are any errors in populating
     */
    protected DeleteCommand(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> commandLine, ArrayList<Task> tasklist) throws MarkUnmarkDeleteException {
        super(tasklist);
        taskNumber = MarkUnmarkDeleteParser.getTaskNumber(commandLine.getValue().get(0), tasklist.size());
    }

    /**
     * Delete task from task list
     */
    public void deleteTask() {
        Task task = tasklist.get(taskNumber - 1);
        tasklist.remove(taskNumber - 1);
        UserInteraction.printMessage("Task deleted: " + task,
                "Now you have " + tasklist.size() + " tasks in the list.");
    }

    /**
     * Execution of command
     */
    @Override
    public void execute() {
        deleteTask();
    }
}
