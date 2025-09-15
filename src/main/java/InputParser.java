import enums.*;
import exceptions.*;

import java.util.AbstractMap;
import java.util.ArrayList;

public class InputParser {
    public static AbstractMap.SimpleEntry<CommandType,ArrayList<String>> parseInput(String input) throws GrootException {
        AbstractMap.SimpleEntry<CommandType, ArrayList<String>> inputs = splitCommand(input); //split and trim the 2 parts of command
        switch (inputs.getKey()) {
            case NONE:
            case LIST:
                break; //return {""} or {"list"}
            case MARK:
            case UNMARK:
            case DELETE:
                parseMarkUnmarkDelete(inputs); // check if task number available and is digit
                break;
            case TODO:
                // add task name to userInput {"taskName"}
                parseTodo(inputs);
                break;
            case DEADLINE:
                //add additional parsing to userInput {"taskName", "by"}
                parseDeadline(inputs);
                break;
            case EVENT:
                //add additional parsing to userInput {"taskName", "from", "by"}
                parseEvent(inputs); //add additional parsing to userInput
                break;
            default:
        }
        return inputs;
    }

    public static CommandType parseCommand(String command) throws GrootException {
        try{
            if (command.isEmpty()) {
                return CommandType.NONE;
            }
            return CommandType.valueOf(command.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new GrootException.InvalidCommandException(command);
        }
    }

    public static TaskType parseTask(CommandType task) throws GrootException {
        try{
            return TaskType.valueOf(task.toString().toUpperCase());
        }catch (IllegalArgumentException e){
            throw new GrootException.InvalidCommandException(task.toString().toLowerCase());
        }
    }

    public static AbstractMap.SimpleEntry<CommandType, ArrayList<String>> splitCommand(String command) throws GrootException {
        int commandSplit = command.indexOf(' ');
        CommandType commandType;
        if (commandSplit == -1) { //only one word
            commandType = parseCommand(command);
            return new AbstractMap.SimpleEntry<>(commandType, new ArrayList<>()) ;
        }
        commandType = parseCommand(command.substring(0,commandSplit));
        String taskInfo = command.substring(commandSplit + 1).trim();
        return new AbstractMap.SimpleEntry<>(commandType, new ArrayList<>(){{add(taskInfo);}}) ;
    }

    public static void parseMarkUnmarkDelete(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> command) throws GrootException {
        InputChecker.checkInput(command);
        InputChecker.checkTaskNumberFormat(command.getValue().get(0), command.getKey());
    }

    public static int getTaskNumber(String taskNumber, int tasklistSize) throws MarkUnmarkDeleteException {
        int taskNumberInt = Integer.parseInt(taskNumber);
        InputChecker.checkTaskNumberValid(taskNumberInt, tasklistSize);
        return taskNumberInt;
    }

    public static void parseTodo(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> input) throws GrootException {
        InputChecker.checkInput(input);
        InputChecker.checkTodoFormat(input.getValue().get(0));
    }

    public static void parseDeadline(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> deadline) throws GrootException {
        InputChecker.checkInput(deadline);
        ArrayList<String> deadlineInput = new ArrayList<>();
        InputChecker.checkDeadlineKeyword(deadline.getValue().get(0));
        String[] input = deadline.getValue().get(0).split("/by", -1); // {taskName, by}
        for (String part : input) {
            part = part.trim();
            deadlineInput.add(part);
        }
        InputChecker.checkDeadlineFormat(deadlineInput);
        deadline.setValue(deadlineInput);
    }

    public static void parseEvent(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> event) throws GrootException {
        InputChecker.checkInput(event);
        ArrayList<String> eventInput = new ArrayList<>();
        InputChecker.checkEventKeywords(event.getValue().get(0));
        String[] input = event.getValue().get(0).split("(/from)|(/to)", -1); // {taskName, from, to}
        for (String part : input) {
            part = part.trim();
            eventInput.add(part);
        }
        InputChecker.checkEventFormat(eventInput);
        event.setValue(eventInput);
    }

    public static Task parseTaskFile(String taskFile) throws GrootException {
        if (taskFile.isBlank()) {
            return null;
        }
        Task task;
        int taskTypeDivider = taskFile.indexOf(']');
        String taskType  = taskFile.substring(0, taskTypeDivider+1);
        taskFile = taskFile.substring(taskTypeDivider + 1);
        int taskDoneDivider  = taskFile.indexOf(']');
        String taskDone  = taskFile.substring(0, taskDoneDivider+1);
        String taskInfo = taskFile.substring(taskDoneDivider + 1);
        ArrayList<String> taskInfoInput;
        task = switch (taskType) {
            case "[T]" -> {
                taskInfoInput = parseTodoFile(taskInfo);
                yield new ToDo(taskInfoInput.get(0));
            }
            case "[D]" -> {
                taskInfoInput = parseDeadlineFile(taskInfo);
                yield new Deadline(taskInfoInput.get(0), taskInfoInput.get(1));
            }
            case "[E]" -> {
                taskInfoInput = parseEventFile(taskInfo);
                yield new Event(taskInfoInput.get(0), taskInfoInput.get(1), taskInfoInput.get(2));
            }
            default -> throw new FileException.FileCorruptedException();
        };
        if (taskDone.equals("[X]")){
            task.setIsDone(true);
        }
        return task;
    }

    public static ArrayList<String> parseTodoFile(String todoFile) throws GrootException {
        ArrayList<String> todoFileInput = new ArrayList<>();
        todoFile = todoFile.trim();
        InputChecker.checkTodoFormat(todoFile);
        todoFileInput.add(todoFile);
        return todoFileInput;
    }

    public static ArrayList<String> parseDeadlineFile(String deadlineFile) throws GrootException {
        ArrayList<String> deadlineFileInput = new ArrayList<>(splitTaskNameFile(deadlineFile));
        deadlineFile = deadlineFileInput.get(1);
        int byDivider = deadlineFile.indexOf("by:");
        if (byDivider == -1) {
            throw new FileException.FileCorruptedException();
        }
        deadlineFileInput.set(1,deadlineFile.substring(byDivider+3).trim());
        InputChecker.checkDeadlineFormat(deadlineFileInput);
        return deadlineFileInput;
    }

    public static  ArrayList<String> parseEventFile(String eventFile) throws GrootException {
        ArrayList<String> eventFileInput = new ArrayList<>(splitTaskNameFile(eventFile));
        eventFile = eventFileInput.get(1);
        int fromDivider = eventFile.indexOf("from:");
        int toDivider = eventFile.indexOf(", to:");
        if (fromDivider == -1 || toDivider == -1) {
            throw new FileException.FileCorruptedException();
        }
        eventFileInput.set(1, eventFile.substring(fromDivider+5, toDivider).trim());
        eventFileInput.add(eventFile.substring(toDivider+3).trim());
        InputChecker.checkEventFormat(eventFileInput);
        return eventFileInput;
    }

    public static ArrayList<String> splitTaskNameFile(String command) throws FileException {
        ArrayList<String> commandInput = new ArrayList<>();
        int taskNameDivider = command.indexOf('|');
        if (taskNameDivider == -1) {
            throw new FileException.FileCorruptedException();
        }
        String taskName  = command.substring(0, taskNameDivider).trim();
        String taskInfo = command.substring(taskNameDivider + 1).trim();
        commandInput.add(taskName);
        commandInput.add(taskInfo);
        return commandInput;
    }
}
