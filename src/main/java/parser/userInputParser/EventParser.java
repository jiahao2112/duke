package parser.userInputParser;

import checker.EventChecker;
import enums.CommandType;
import exceptions.GrootException;

import java.util.AbstractMap;
import java.util.ArrayList;

public class EventParser {

    protected static void parseEvent(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> event) throws GrootException {
        ArrayList<String> eventInput = event.getValue();
        EventChecker.checkEventKeywords(eventInput.get(0));
        splitEventInformation(eventInput);
        EventChecker.checkEventFormat(eventInput);
        event.setValue(eventInput);
    }

    private static void splitEventInformation(ArrayList<String> eventInfo) {
        String[] eventParts = eventInfo.get(0).split("/from|/to", -1);
        String taskName = eventParts[0].trim();
        String from = eventParts[1].trim();
        String to = eventParts[2].trim();
        eventInfo.set(0, taskName); //set task name to index 0
        eventInfo.add(from); //add from
        eventInfo.add(to); //add to
    }
}
