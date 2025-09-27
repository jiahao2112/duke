package exceptions;

//For any exceptions regarding deadline task
public class DeadlineException extends TaskException {
    public DeadlineException(String message) {
        super(message + " Usage: deadline <task name> /by <dd/MM/yyyy> <HH:mm (24-hour)>");
    }

    public static class MissingDeadlineByException extends DeadlineException {
        public MissingDeadlineByException() {
            super("Missing by-date and time in deadline command.");
        }
    }

    public static class MissingDeadlineByKeywordException extends DeadlineException {
        public MissingDeadlineByKeywordException() {
            super("Missing /by keyword in deadline command.");
        }
    }

    public static class MissingDeadlineTaskNameException extends DeadlineException {
        public MissingDeadlineTaskNameException() {
            super("Missing <task name> in deadline command.");
        }
    }

    public static class InvalidDeadlineDateTimeException extends DeadlineException {
        public InvalidDeadlineDateTimeException() {
            super("Invalid date/time.");
        }
    }
}





