import enums.CommandType;
import exceptions.*;

import java.util.AbstractMap;
import java.util.ArrayList;

public class InputChecker {
    public static void checkTaskNumberFormat(String taskNumber, CommandType markUnmark) throws MarkUnmarkDeleteException {
        if (taskNumber.isEmpty()) { //task number missing
            throw new MarkUnmarkDeleteException.MissingTaskNumberException(markUnmark.toString().toLowerCase());
        } else if (!taskNumber.matches("\\d+")) { //task number not a digit
            throw new MarkUnmarkDeleteException.InvalidTaskNumberException(markUnmark.toString().toLowerCase());
        }
    }

    public static void checkInput (AbstractMap.SimpleEntry<CommandType, ArrayList<String>> input) throws GrootException {
        if (input.getValue().isEmpty()) {
            switch (input.getKey()) { // minimum missing these parameters
                case MARK:
                    throw new MarkUnmarkDeleteException.MissingTaskNumberException("mark");
                case UNMARK:
                    throw new MarkUnmarkDeleteException.MissingTaskNumberException("unmark");
                case DELETE:
                    throw new MarkUnmarkDeleteException.MissingTaskNumberException("delete");
                case TODO:
                    throw new TodoException.MissingTodoTaskNameException();
                case DEADLINE:
                    throw new DeadlineException.MissingDeadlineByKeywordException();
                case EVENT:
                    throw new EventException.MissingEventKeywordsException();
            }
        }
    }

    public static void checkTaskNumberValid(int taskNumber, int tasklistSize) throws MarkUnmarkDeleteException {
        if (taskNumber > tasklistSize) {
            throw new MarkUnmarkDeleteException.TaskNotFoundException();
        }
    }

    public static void checkTodoFormat(String todo) throws TodoException {
        if (todo.isEmpty()) {
            throw new TodoException.MissingTodoTaskNameException();
        }
    }

    public static void checkDeadlineKeyword(String deadline) throws DeadlineException {
        if (!deadline.contains("/by")) { //ensure /by keyword is in string
            throw new DeadlineException.MissingDeadlineByKeywordException();
        }
    }

    public static void checkDeadlineFormat(ArrayList<String> deadline) throws DeadlineException {
        if (deadline.get(0).isEmpty()) { //missing task name
            throw new DeadlineException.MissingDeadlineTaskNameException();
        } else if (deadline.get(1).isEmpty()) { //missing by
            throw new DeadlineException.MissingDeadlineByException();
        }
    }

    public static void checkEventKeywords(String event) throws EventException {
        boolean hasFromKeyword = event.contains("/from");
        boolean hasToKeyword = event.contains("/to");
        if (!hasFromKeyword && !hasToKeyword) { //ensure both keywords are in string
            throw new EventException.MissingEventKeywordsException();
        } else if (!hasFromKeyword) { //check if /from is missing
            throw new EventException.MissingEventFromKeywordException();
        } else if (!hasToKeyword) { //check if /to keyword is missing
            throw new EventException.MissingEventToKeywordException();
        }
    }

    public static void checkEventFormat(ArrayList<String> event) throws EventException {
        if (event.get(0).isEmpty()) { //missing <task name>
            throw new EventException.MissingEventTaskNameException();
        } else if (event.get(1).isEmpty()) { //missing <from>
            throw new EventException.MissingEventFromException();
        } else if (event.get(2).isEmpty()) { //missing <to>
            throw new EventException.MissingEventToException();
        }
    }

    public static void checkTaskStatus (Task task, boolean markDone) throws MarkUnmarkDeleteException {
        if (task.getIsDone() == markDone) {
            throw new MarkUnmarkDeleteException.TaskAlreadyMarkedException(markDone? "done":"not done");
        }
    }

}
