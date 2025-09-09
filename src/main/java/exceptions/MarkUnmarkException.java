package exceptions;

//For any exceptions in marking or unmarking task
public class MarkUnmarkException extends TaskException {
    public MarkUnmarkException(String message, String markUnmark) {
        super(message + (!markUnmark.isEmpty() ? " Usage: " + markUnmark + " <task number>" : ""));
    }

    public static class MissingTaskNumberException extends MarkUnmarkException {
        public MissingTaskNumberException(String markUnmark) {
            super("Missing task number.", markUnmark);
        }
    }

    public static class InvalidTaskNumberException extends MarkUnmarkException {
        public InvalidTaskNumberException(String markUnmark) {
            super("Invalid task number.", markUnmark);
        }
    }

    public static class TaskNotFoundException extends MarkUnmarkException {
        public TaskNotFoundException() {
            super("Task not found in task list.", "");
        }
    }

    public static class TaskAlreadyMarkedException extends MarkUnmarkException {
        public TaskAlreadyMarkedException(String doneNotDone) {
            super("Task is already marked in task list as " + doneNotDone + ".", "");
        }
    }
}

