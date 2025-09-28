package commands;

import enums.CommandType;
import exceptions.DateTimeException;
import exceptions.FileException;
import exceptions.GrootException;
import parser.DateTimeParser;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;
import ui.UserInteraction;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;

/**
 * Used for adding task to task list
 */
public class AddCommand extends Command {
    /*
     * commandType is to know which task user wants to add
     * taskName is the name of task that user wants to add
     * by is the /by section for deadline command
     * from and to are the /from and /to sections for event command
     */
    private final CommandType commandType;
    private final String taskName;
    private LocalDateTime by;
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Construct required parameters for the different tasks
     *
     * @param commandLine user's input, where key is command type and value is the information for command
     * @param tasklist    task list passed from task manager, shared task list
     * @throws GrootException if there are any errors in constructing
     */
    protected AddCommand(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> commandLine, ArrayList<Task> tasklist) throws GrootException {
        super(tasklist);
        this.commandType = commandLine.getKey();
        this.taskName = commandLine.getValue().get(0);
        if (commandType == CommandType.DEADLINE) {
            String by = commandLine.getValue().get(1);
            this.by = DateTimeParser.parseDateTime(by);
        } else if (commandType == CommandType.EVENT) {
            String from = commandLine.getValue().get(1);
            String to = commandLine.getValue().get(2);
            this.from = DateTimeParser.parseDateTime(from);
            this.to = DateTimeParser.parseDateTime(to);
        }
    }

    /*
     * Creates the task to be added to task list
     */
    private Task createTask() {
        Task task;
        task = switch (commandType) {
            case TODO -> new ToDo(taskName);
            case DEADLINE -> new Deadline(taskName, by);
            case EVENT -> new Event(taskName, from, to);
            default -> null;
        };
        return task;
    }

    /**
     * Creates task from a command line.
     * Used for tasks in task list file
     *
     * @param commandLine parsed task information from tasklist file
     * @return todo, deadline or event task based on task information
     * @throws FileException if there is any error in creating the task where the only reason it cannot be created is where the tasklist file is corrupted
     */
    public static Task createTask(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> commandLine) throws FileException { //overload function for file parsing
        try {
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
        } catch (DateTimeException e) {
            throw new FileException.FileCorruptedException();

        }
    }

    /**
     * Adds task to tasklist
     */
    public void addTask() {
        Task task = createTask();
        if (task == null) {
            return;
        }
        tasklist.add(task);
        UserInteraction.printMessage("Task added: " + task,
                "Now you have " + tasklist.size() + " tasks in the list.");
    }

    /**
     * Execution of the command
     */
    @Override
    public void execute() {
        addTask();
    }
}
