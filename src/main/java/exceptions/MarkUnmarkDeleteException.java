package exceptions;

//For any exceptions in marking or unmarking task
public class MarkUnmarkDeleteException extends TaskException {
    public MarkUnmarkDeleteException(String message, String markUnmark) {
        super(message + (!markUnmark.isEmpty() ? " Usage: " + markUnmark + " <task number>" : ""));
    }

    public static class MissingTaskNumberException extends MarkUnmarkDeleteException {
        public MissingTaskNumberException(String markUnmarkDelete) {
            super("Missing task number.", markUnmarkDelete);
        }
    }

    public static class InvalidTaskNumberException extends MarkUnmarkDeleteException {
        public InvalidTaskNumberException(String markUnmarkDelete) {
            super("Invalid task number.", markUnmarkDelete);
        }
    }

    public static class TaskNotFoundException extends MarkUnmarkDeleteException {
        public TaskNotFoundException() {
            super("Task not found in task list.", "");
        }
    }

    public static class TaskAlreadyMarkedException extends MarkUnmarkDeleteException {
        public TaskAlreadyMarkedException(String doneNotDone) {
            super("Task is already marked in task list as " + doneNotDone + ".", "");
        }
    }
}

