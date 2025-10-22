package parser.userInputParser;

import checker.UpdateChecker;
import enums.CommandType;
import exceptions.UpdateException;

import java.util.AbstractMap;
import java.util.ArrayList;

public class UpdateParser {
    public static void parseUpdate(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> update)
            throws UpdateException {
        ArrayList<String> updateInput = update.getValue();
        splitUpdateInformation(updateInput);
        update.setValue(updateInput);
    }

    private static void splitUpdateInformation(ArrayList<String> updateInput) throws UpdateException {
        String info = updateInput.get(0);
        info = info.trim();
        String[] infoArray = info.split(",");
        UpdateChecker.checkTaskNumberFormat(infoArray[0]);
        updateInput.set(0, infoArray[0]); //task number
        for (int i = 1; i < infoArray.length; i++) { // add update fields
            updateInput.add(infoArray[i].trim());
        }
    }

    public static int getTaskNumber(String taskNumber, int tasklistSize) throws UpdateException {
        int taskNumberInt = Integer.parseInt(taskNumber);
        UpdateChecker.checkTaskNumberValid(taskNumberInt, tasklistSize);
        return taskNumberInt;
    }
}
