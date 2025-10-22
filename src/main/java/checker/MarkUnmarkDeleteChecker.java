package checker;

import enums.CommandType;
import exceptions.MarkUnmarkDeleteException;

/**
 * Used for checking the various conditions required for mark, unmark and delete commands.
 * Mark, Unmark and Delete commands have the same format
 */

public class MarkUnmarkDeleteChecker {

    /**
     * Checks if task number given by user is empty or not digit
     *
     * @param taskNumber       task number given by user
     * @param markUnmarkDelete command type, i.e. mark, unmark or delete
     * @throws MarkUnmarkDeleteException.MissingTaskNumberException if task number is missing, i.e. taskNumber = ""
     * @throws MarkUnmarkDeleteException.InvalidTaskNumberException if task number is not a digit
     */
    public static void checkTaskNumberFormat(String taskNumber, CommandType markUnmarkDelete) throws MarkUnmarkDeleteException {
        checkTaskNumberEmpty(taskNumber, markUnmarkDelete); //check if there is task number i.e. not empty
        checkTaskNumberDigits(taskNumber, markUnmarkDelete); //check if task number is a digit
    }

    /*
     * Used by checkTaskNumberFormat
     * Checks if task number is empty
     */
    private static void checkTaskNumberEmpty(String taskNumber, CommandType markUnmarkDelete) throws MarkUnmarkDeleteException.MissingTaskNumberException {
        if (taskNumber.isEmpty()) {
            throw new MarkUnmarkDeleteException.MissingTaskNumberException(markUnmarkDelete.toString().toLowerCase());
        }
    }

    /*
     * Used by checkTaskNumberFormat
     * Checks if task number is a digit
     */
    private static void checkTaskNumberDigits(String taskNumber, CommandType markUnmarkDelete) throws MarkUnmarkDeleteException.InvalidTaskNumberException {
        if (!taskNumber.matches("\\d+")) {
            throw new MarkUnmarkDeleteException.InvalidTaskNumberException(markUnmarkDelete.toString().toLowerCase());
        }
    }

    /**
     * Checks if task number is within size of task list
     *
     * @param taskNumber   task number given by user
     * @param tasklistSize size of task list
     * @throws MarkUnmarkDeleteException.TaskNotFoundException if task number is out of task list range, i.e. task number is more than task list size or less than 1
     */
    public static void checkTaskNumberValid(int taskNumber, int tasklistSize)
            throws MarkUnmarkDeleteException.TaskNotFoundException {
        if (taskNumber > tasklistSize || taskNumber <= 0) {
            throw new MarkUnmarkDeleteException.TaskNotFoundException();
        }
    }

    /**
     * Checks if task is already marked as user's intention
     * Used by mark and unmark command
     *
     * @param taskIsDone task is marked as done or undone
     * @param isDone     user's intention to mark the task done or undone
     * @throws MarkUnmarkDeleteException.TaskAlreadyMarkedException if task is already marked according to user's intention
     */
    public static void checkTaskStatus(boolean taskIsDone, boolean isDone)
            throws MarkUnmarkDeleteException.TaskAlreadyMarkedException {
        if (taskIsDone == isDone) {
            throw new MarkUnmarkDeleteException.TaskAlreadyMarkedException(isDone ? "done" : "not done");
        }
    }
}
