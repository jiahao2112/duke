package exceptions;

public class ViewException extends GrootException {
    public ViewException(String message) {
        super(message);
    }

    public static class InvalidViewDateException extends ViewException {
        public InvalidViewDateException() {
            super("Invalid date. Usage: view <dd/MM/yyyy>");
        }
    }

    public static class NoTaskForViewException extends ViewException {
        public NoTaskForViewException() {
            super("No task available for given date.");
        }
    }
}
