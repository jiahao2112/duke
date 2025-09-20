package checker;

import exceptions.EventException;

import java.util.ArrayList;

public class EventChecker {

    public static void checkEventFormat(ArrayList<String> event) throws EventException {
        try {
            checkEventTaskNameMissing(event.get(0));
            checkEventFromMissing(event.get(1));
            checkEventToMissing(event.get(2));
        } catch (IndexOutOfBoundsException e) {
            throw new EventException("Invalid event format.");
        }
    }

    private static void checkEventTaskNameMissing(String taskName) throws EventException.MissingEventTaskNameException {
        if (taskName.isEmpty()) {
            throw new EventException.MissingEventTaskNameException();
        }
    }

    private static void checkEventFromMissing(String eventInput) throws EventException.MissingEventFromException {
        if (eventInput.isEmpty()) {
            throw new EventException.MissingEventFromException();
        }
    }

    private static void checkEventToMissing(String eventInput) throws EventException.MissingEventToException {
        if (eventInput.isEmpty()) {
            throw new EventException.MissingEventToException();
        }
    }

    public static void checkEventKeywords(String eventInput) throws EventException {
        boolean hasFromKeyword = eventInput.contains("/from");
        boolean hasToKeyword = eventInput.contains("/to");
        if (!hasFromKeyword && !hasToKeyword) {
            throw new EventException.MissingEventKeywordsException();
        } else if (!hasFromKeyword) {
            throw new EventException.MissingEventFromKeywordException();
        } else if (!hasToKeyword) {
            throw new EventException.MissingEventToKeywordException();
        }
    }
}
