package commands;

import checker.MarkUnmarkDeleteChecker;
import enums.CommandType;
import exceptions.MarkUnmarkDeleteException;
import parser.userInputParser.MarkUnmarkDeleteParser;
import tasks.Task;
import ui.UserInteraction;

import java.util.AbstractMap;
import java.util.ArrayList;

public class MarkTaskCommand extends Command {
    int taskNumber;
    boolean markDone;

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

    public void markTask() throws MarkUnmarkDeleteException {
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

    @Override
    public void execute() throws MarkUnmarkDeleteException {
        markTask();
    }

}
