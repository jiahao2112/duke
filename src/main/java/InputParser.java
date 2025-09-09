import exceptions.*;

import java.util.ArrayList;

public class InputParser {
    public static ArrayList<String> parseInput(String command) throws GrootException {
        String[] input = splitCommand(command); //split and trim the 2 parts of command
        ArrayList<String> userInput = new ArrayList<>();
        userInput.add(input[0]);
        switch (input[0]) {
            case "":
            case "list":
                break; //return {""} or {"list"}
            case "mark":
            case "unmark":
                userInput.add(parseMarkUnmark(input[0], input[1])); // check if task number available and is digit
                break;
            case "delete":
                userInput.add(parseDelete(input[1]));
                break;
            case "todo":
                // add task name to userInput {"taskName"}
                userInput.addAll(parseTodo(input[1]));
                break;
            case "deadline":
                //add additional parsing to userInput {"taskName", "by"}
                userInput.addAll(parseDeadline(input[1]));
                break;
            case "event":
                //add additional parsing to userInput {"taskName", "from", "by"}
                userInput.addAll(parseEvent(input[1])); //add additional parsing to userInput
                break;
            default:
                throw new GrootException.InvalidCommandException(input[0]);
        }
        return userInput;
    }

    public static String[] splitCommand(String command) {
        int commandSplit = command.indexOf(' ');
        if (commandSplit == -1) { //only one word
            return new String[]{command, ""};
        }
        String taskType = command.substring(0, commandSplit).trim();
        String taskInfo = command.substring(commandSplit + 1).trim();
        return new String[]{taskType, taskInfo};
    }

    public static String parseMarkUnmark(String markUnmark, String taskNumber) throws MarkUnmarkDeleteException {
        InputChecker.checkTaskNumberFormat(taskNumber, markUnmark);
        //return taskNumber if pass check
        return taskNumber;
    }
    public static int getTaskNumber(String taskNumber, int tasklistSize) throws MarkUnmarkDeleteException {
        int taskNumberInt = Integer.parseInt(taskNumber);
        InputChecker.checkTaskNumberValid(taskNumberInt, tasklistSize);
        return taskNumberInt;
    }
    public static String parseDelete(String taskNumber) throws MarkUnmarkDeleteException {
        InputChecker.checkTaskNumberFormat(taskNumber,"delete");
        return taskNumber;
    }

    public static ArrayList<String> parseTodo(String input) throws TodoException {
        InputChecker.checkTodoFormat(input);
        ArrayList<String> toDoInput = new ArrayList<>();
        toDoInput.add(input);
        return toDoInput;
    }

    public static ArrayList<String> parseDeadline(String deadline) throws DeadlineException {
        ArrayList<String> deadlineInput = new ArrayList<>();
        InputChecker.checkDeadlineKeyword(deadline);
        String[] input = deadline.split("/by", -1); // {taskName, by}
        for (String part : input) {
            part = part.trim();
            deadlineInput.add(part);
        }
        InputChecker.checkDeadlineFormat(deadlineInput);
        return deadlineInput;
    }

    public static ArrayList<String> parseEvent(String event) throws EventException {
        ArrayList<String> eventInput = new ArrayList<>();
        InputChecker.checkEventKeywords(event);
        String[] input = event.split("(/from)|(/to)", -1); // {taskName, from, to}
        for (String part : input) {
            part = part.trim();
            eventInput.add(part);
        }
        InputChecker.checkEventFormat(eventInput);
        return eventInput;
    }
}
