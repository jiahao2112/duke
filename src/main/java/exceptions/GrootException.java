package exceptions;

public class GrootException extends Exception {
    GrootException(String message) {
        super(message);
    }

    public static class InvalidCommandException extends GrootException {
        public InvalidCommandException(String message) {
            super("Invalid command: " + message);
        }
    }

    public static class EmptyListException extends GrootException {
        public EmptyListException() {
            super("There are no tasks yet.");
        }
    }
}




