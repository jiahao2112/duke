import enums.CommandType;
import exceptions.GrootException;
import exceptions.MarkUnmarkDeleteException;

import java.util.AbstractMap;
import java.util.ArrayList;

public class MarkUnmarkDeleteParser {

    protected static void parseMarkUnmarkDelete(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> command) throws GrootException {
        MarkUnmarkDeleteChecker.checkTaskNumberFormat(command.getValue().get(0), command.getKey());
    }

    public static int getTaskNumber(String taskNumber, int tasklistSize) throws MarkUnmarkDeleteException {
        int taskNumberInt = Integer.parseInt(taskNumber);
        MarkUnmarkDeleteChecker.checkTaskNumberValid(taskNumberInt, tasklistSize);
        return taskNumberInt;
    }
}
