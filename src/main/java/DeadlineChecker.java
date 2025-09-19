import exceptions.DeadlineException;

import java.util.ArrayList;

public class DeadlineChecker {
    public static void checkDeadlineByKeyword(String deadlineInput) throws DeadlineException.MissingDeadlineByKeywordException {
        if (!deadlineInput.contains("/by")) {
            throw new DeadlineException.MissingDeadlineByKeywordException();
        }
    }

    public static void checkDeadlineFormat(ArrayList<String> deadlineInput) throws DeadlineException {
        try {
            checkDeadlineTaskName(deadlineInput.get(0));
            checkDeadlineBy(deadlineInput.get(1));
        } catch (IndexOutOfBoundsException e) {
            throw new DeadlineException("Invalid deadline format.");
        }
    }

    private static void checkDeadlineTaskName(String taskName) throws DeadlineException.MissingDeadlineTaskNameException {
        if (taskName.isEmpty()) {
            throw new DeadlineException.MissingDeadlineTaskNameException();
        }
    }

    private static void checkDeadlineBy(String by) throws DeadlineException.MissingDeadlineByException {
        if (by.isEmpty()) {
            throw new DeadlineException.MissingDeadlineByException();
        }
    }
}
