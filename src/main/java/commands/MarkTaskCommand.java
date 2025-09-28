package commands;

import checker.MarkUnmarkDeleteChecker;
import enums.CommandType;
import exceptions.MarkUnmarkDeleteException;
import parser.userInputParser.MarkUnmarkDeleteParser;
import tasks.Task;
import ui.UserInteraction;

import java.util.AbstractMap;
import java.util.ArrayList;

/**
 * Used to mark the task in tasklist as done or not done
 */
public class MarkTaskCommand extends Command {
    int taskNumber;
    boolean markDone;

    /**
     * Populate required parameters
     *
     * @param commandLine parsed user's input
     * @param tasklist    tasklist from task manager
     * @throws MarkUnmarkDeleteException if there are any errors in populating
     */
    protected MarkTaskCommand(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> commandLine, ArrayList<Task> tasklist) throws MarkUnmarkDeleteException {
        super(tasklist);
        taskNumber = MarkUnmarkDeleteParser.getTaskNumber(commandLine.getValue().get(0), tasklist.size());
        switch (commandLine.getKey()) {
            case MARK:
                markDone = true;
                break;
            case UNMARK:
                markDone = false;
        }
    }

    /**
     * Mark task as done or not done
     *
     * @throws MarkUnmarkDeleteException.TaskAlreadyMarkedException if task is already marked as done or not done
     */
    public void markTask() throws MarkUnmarkDeleteException.TaskAlreadyMarkedException {
        Task task;
        task = tasklist.get(taskNumber - 1);
        MarkUnmarkDeleteChecker.checkTaskStatus(task.getIsDone(), markDone);
        task.setIsDone(markDone);
        if (markDone) {
            UserInteraction.printMessage("Task marked as done: " + task);
        } else {
            UserInteraction.printMessage("Task marked as not done yet: " + task);
        }
    }

    /**
     * Execution of command
     *
     * @throws MarkUnmarkDeleteException if task is already marked
     */
    @Override
    public void execute() throws MarkUnmarkDeleteException {
        markTask();
    }

}
