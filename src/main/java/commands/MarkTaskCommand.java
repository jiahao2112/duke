package commands;

import checker.TaskNumberChecker;
import enums.CommandType;
import exceptions.TaskNumberException;
import gui.GrootGUI;
import parser.userInputParser.TaskNumberParser;
import tasks.Task;

import java.util.AbstractMap;
import java.util.ArrayList;

/**
 * Used to mark the task in tasklist as done or not done
 */
public class MarkTaskCommand extends Command {
    boolean markDone;
    Task task;

    /**
     * Populate required parameters
     *
     * @param commandLine parsed user's input
     * @param tasklist    tasklist from task manager
     * @throws TaskNumberException if there are any errors in populating
     */
    protected MarkTaskCommand(AbstractMap.SimpleEntry<CommandType,
            ArrayList<String>> commandLine, ArrayList<Task> tasklist) throws TaskNumberException {
        super(tasklist);
        int taskNumber = TaskNumberParser.getTaskNumber(commandLine.getValue().get(0), tasklist.size());

        switch (commandLine.getKey()) {
        case MARK -> markDone = true;
        case UNMARK -> markDone = false;
        }

        task = tasklist.get(taskNumber - 1);
        TaskNumberChecker.checkTaskStatus(task.getIsDone(), markDone);
    }

    /**
     * Mark task as done or not done
     */
    public String markTask() {
        task.setIsDone(markDone);

        if (markDone) {
            return GrootGUI.buildReply("Task marked as done: " + task);
        } else {
            return GrootGUI.buildReply("Task marked as not done yet: " + task);
        }
    }

    /**
     * Execution of command
     */
    @Override
    public String execute() {
        return markTask();
    }

}
