package commands;

import enums.CommandType;
import exceptions.*;
import parser.DateTimeParser;
import tasks.*;
import ui.UserInteraction;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;

public class AddCommand extends Command {
    CommandType commandType;
    String taskName;
    LocalDateTime by;
    LocalDateTime from;
    LocalDateTime to;

    protected AddCommand(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> commandLine, ArrayList<Task> tasklist) throws GrootException {
        super(tasklist);
        this.commandType = commandLine.getKey();
        this.taskName = commandLine.getValue().get(0);
        if (commandType == CommandType.DEADLINE) {
            String by = commandLine.getValue().get(1);
            this.by = DateTimeParser.parseDateTime(by);
        }else if  (commandType == CommandType.EVENT) {
            String from = commandLine.getValue().get(1);
            String to = commandLine.getValue().get(2);
            this.from = DateTimeParser.parseDateTime(from);
            this.to = DateTimeParser.parseDateTime(to);
        }
    }

    private Task createTask(){
            Task task;
            task = switch (commandType) {
                case TODO -> new ToDo(taskName);
                case DEADLINE -> new Deadline(taskName, by);
                case EVENT -> new Event(taskName, from, to);
                default -> null;
            };
            return task;
    }

    public static Task createTask(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> commandLine) throws FileException{ //overload function for file parsing
        try{
            Task task;
            task = switch (commandLine.getKey()) {
                case TODO -> new ToDo(commandLine.getValue().get(0));
                case DEADLINE ->
                        new Deadline(commandLine.getValue().get(0), DateTimeParser.parseDateTime(commandLine.getValue().get(1)));
                case EVENT ->
                        new Event(commandLine.getValue().get(0), DateTimeParser.parseDateTime(commandLine.getValue().get(1)), DateTimeParser.parseDateTime(commandLine.getValue().get(2)));
                default -> null;
            };
            return task;
        }catch (DateTimeException e){
            throw new FileException.FileCorruptedException();

        }
    }

    public void addTask(){
        Task task = createTask();
        if (task == null) {
            return;
        }
        tasklist.add(task);
        UserInteraction.printMessage("Task added: " + task,
                "Now you have " + tasklist.size() + " tasks in the list.");
    }

    @Override
    public void execute(){
        addTask();
    }
}
