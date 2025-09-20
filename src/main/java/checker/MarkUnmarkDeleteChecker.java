package checker;

import enums.CommandType;
import exceptions.MarkUnmarkDeleteException;

public class MarkUnmarkDeleteChecker {

    public static void checkTaskNumberFormat(String taskNumber, CommandType markUnmarkDelete) throws MarkUnmarkDeleteException {
        checkTaskNumberEmpty(taskNumber, markUnmarkDelete); //check if there is task number i.e. not empty
        checkTaskNumberDigits(taskNumber, markUnmarkDelete); //check if task number is a digit
    }

    private static void checkTaskNumberEmpty(String taskNumber, CommandType markUnmarkDelete) throws MarkUnmarkDeleteException.MissingTaskNumberException {
        if (taskNumber.isEmpty()) {
            throw new MarkUnmarkDeleteException.MissingTaskNumberException(markUnmarkDelete.toString().toLowerCase());
        }
    }

    private static void checkTaskNumberDigits(String taskNumber, CommandType markUnmarkDelete) throws MarkUnmarkDeleteException.InvalidTaskNumberException {
        if (!taskNumber.matches("\\d+")) {
            throw new MarkUnmarkDeleteException.InvalidTaskNumberException(markUnmarkDelete.toString().toLowerCase());
        }
    }

    public static void checkTaskNumberValid(int taskNumber, int tasklistSize) throws MarkUnmarkDeleteException.TaskNotFoundException {
        if (taskNumber > tasklistSize || taskNumber <= 0) {
            throw new MarkUnmarkDeleteException.TaskNotFoundException();
        }
    }

    public static void checkTaskStatus(boolean taskIsDone, boolean isDone) throws MarkUnmarkDeleteException.TaskAlreadyMarkedException {
        if (taskIsDone == isDone) {
            throw new MarkUnmarkDeleteException.TaskAlreadyMarkedException(isDone ? "done" : "not done");
        }
    }
}
