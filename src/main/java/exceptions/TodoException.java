package exceptions;

//For any exceptions regarding todo task
public class TodoException extends TaskException {
    public TodoException(String message) {
        super(message + " Usage: todo <task name>");
    }

    public static class MissingTodoTaskNameException extends TodoException {
        public MissingTodoTaskNameException() {
            super("Missing <task name> in todo command.");
        }
    }
}

