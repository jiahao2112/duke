package exceptions;

/**
 * Thrown for any errors regarding mark, unmark and delete commands
 */
public class MarkUnmarkDeleteException extends TaskException {
    /**
     * @param message    message form sub exceptions
     * @param markUnmark command type to be shown for usage template
     */
    public MarkUnmarkDeleteException(String message, String markUnmark) {
        super(message + (!markUnmark.isEmpty() ? " Usage: " + markUnmark + " <task number>" : ""));
    }

    /**
     * Thrown when task number is missing
     */
    public static class MissingTaskNumberException extends MarkUnmarkDeleteException {
        public MissingTaskNumberException(String markUnmarkDelete) {
            super("Missing task number.", markUnmarkDelete);
        }
    }

    /**
     * Thrown when task number is not a digit
     */
    public static class InvalidTaskNumberException extends MarkUnmarkDeleteException {
        public InvalidTaskNumberException(String markUnmarkDelete) {
            super("Invalid task number.", markUnmarkDelete);
        }
    }

    /**
     * Thrown when task is not in tasklist
     */
    public static class TaskNotFoundException extends MarkUnmarkDeleteException {
        public TaskNotFoundException() {
            super("Task not found in task list.", "");
        }
    }

    /**
     * Thrown when task is already marked as intended
     */
    public static class TaskAlreadyMarkedException extends MarkUnmarkDeleteException {
        public TaskAlreadyMarkedException(String doneNotDone) {
            super("Task is already marked in task list as " + doneNotDone + ".", "");
        }
    }
}

