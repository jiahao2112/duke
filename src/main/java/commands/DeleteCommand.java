package commands;

import enums.CommandType;
import exceptions.GrootException;
import exceptions.MarkUnmarkDeleteException;
import parser.userInputParser.MarkUnmarkDeleteParser;
import tasks.Task;
import ui.UserInteraction;

import java.util.AbstractMap;
import java.util.ArrayList;

public class DeleteCommand extends Command {
    int taskNumber;

    protected DeleteCommand(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> commandLine, ArrayList<Task> tasklist) throws MarkUnmarkDeleteException {
        super(tasklist);
        taskNumber = MarkUnmarkDeleteParser.getTaskNumber(commandLine.getValue().get(0), tasklist.size());
    }
    public void deleteTask(){
        Task task = tasklist.get(taskNumber - 1);
        tasklist.remove(taskNumber - 1);
        UserInteraction.printMessage("Task deleted: " + task,
                "Now you have " + tasklist.size() + " tasks in the list.");
    }

    @Override
    public void execute(){
        deleteTask();
    }
}
