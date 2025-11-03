package parser.userInputParser;

import checker.TaskNumberChecker;
import enums.CommandType;
import exceptions.TaskNumberException;

import java.util.AbstractMap;
import java.util.ArrayList;

public class UpdateParser {
    public static void parseUpdate(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> update)
            throws TaskNumberException {
        assert update != null;
        assert update.getValue() != null;

        ArrayList<String> updateInput = update.getValue();

        splitUpdateInformation(updateInput);
        update.setValue(updateInput);
    }

    private static void splitUpdateInformation(ArrayList<String> updateInput) throws TaskNumberException {
        assert updateInput != null;
        assert !updateInput.isEmpty();
        assert updateInput.get(0) != null;

        String info = updateInput.get(0);
        info = info.trim();
        String[] infoArray = info.split(",");

        assert infoArray.length > 0;

        TaskNumberChecker.checkTaskNumberFormat(infoArray[0], CommandType.UPDATE);
        updateInput.set(0, infoArray[0]); //task number
        for (int i = 1; i < infoArray.length; i++) { // add update fields
            updateInput.add(infoArray[i].trim());
        }
    }
}
