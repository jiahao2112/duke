import exceptions.*;

import java.util.ArrayList;

public class InputChecker {
    public static void checkTaskNumberFormat(String taskNumber, String markUnmark) throws MarkUnmarkException {
        if (taskNumber.isEmpty()) { //task number missing
            throw new MarkUnmarkException.MissingTaskNumberException(markUnmark);
        } else if (!taskNumber.matches("\\d+")) { //task number not a digit
            throw new MarkUnmarkException.InvalidTaskNumberException(markUnmark);
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


}
