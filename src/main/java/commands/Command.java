package commands;

import enums.CommandType;
import exceptions.*;
import tasks.Task;

import java.util.AbstractMap;
import java.util.ArrayList;

public abstract class Command {
    protected ArrayList<Task> tasklist;

    public Command(ArrayList<Task> tasklist) {
        this.tasklist = tasklist;
    }

    public static Command createCommand(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> commandLine, ArrayList<Task> tasklist) throws GrootException {
        try {
            return switch (commandLine.getKey()) {
                case MARK, UNMARK -> new MarkTaskCommand(commandLine, tasklist);
                case LIST, VIEW -> new ShowListCommand(commandLine, tasklist);
                case DELETE -> new DeleteCommand(commandLine, tasklist);
                case TODO, DEADLINE, EVENT -> new AddCommand(commandLine, tasklist);
                case NONE -> null;
                case BYE -> new ExitCommand(tasklist);
            };
        }catch (DateTimeException e){
            switch (commandLine.getKey()) {
                case DEADLINE -> throw new DeadlineException.InvalidDeadlineDateTimeException();
                case EVENT -> throw new EventException.InvalidEventDateTimeException();
                case VIEW -> throw new ViewException.InvalidViewDateException();
            }
            return null;
        }
    }

    public abstract void execute() throws GrootException;
}
