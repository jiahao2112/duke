package exceptions;

//For any exceptions regarding event task
public class EventException extends TaskException {
    public EventException(String message) {
        super(message + " Usage: event <task name> /from <from> /to <to>");
    }

    public static class MissingEventKeywordsException extends EventException {
        public MissingEventKeywordsException() {
            super("Missing /from and /to keywords in event command.");
        }
    }

    public static class MissingEventFromKeywordException extends EventException {
        public MissingEventFromKeywordException() {
            super("Missing /from keyword in event command.");
        }
    }

    public static class MissingEventToKeywordException extends EventException {
        public MissingEventToKeywordException() {
            super("Missing /to keyword in event command.");
        }
    }

    public static class MissingEventTaskNameException extends EventException {
        public MissingEventTaskNameException() {
            super("Missing <task name> in event command.");
        }
    }

    public static class MissingEventFromException extends EventException {
        public MissingEventFromException() {
            super("Missing <from> in event command.");
        }
    }

    public static class MissingEventToException extends EventException {
        public MissingEventToException() {
            super("Missing <to> in event command.");
        }
    }
}

