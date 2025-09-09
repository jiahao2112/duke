package exceptions;

//Prepare for any generic task exception
public class TaskException extends GrootException {
    public TaskException(String message) {
        super(message);
    }
}