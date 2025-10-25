package commands;

import enums.CommandType;
import exceptions.TaskNumberException;
import parser.userInputParser.TaskNumberParser;
import tasks.Task;

import java.util.AbstractMap;
import java.util.ArrayList;

public class CloneCommand extends Command {
    Task task;

    public CloneCommand(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> command,
                        ArrayList<Task> tasklist) throws TaskNumberException {
        super(tasklist);
        String taskNum =  command.getValue().get(0);
        int taskNumber = TaskNumberParser.getTaskNumber(taskNum, tasklist.size());
        this.task = tasklist.get(taskNumber-1);
    }

    private void cloneTask(){
        tasklist.add(task);
    }

    @Override
    public void execute() {
        cloneTask();
    }
}
