package parser.userInputParser;

import checker.MarkUnmarkDeleteChecker;
import enums.CommandType;
import exceptions.GrootException;
import exceptions.MarkUnmarkDeleteException;

import java.util.AbstractMap;
import java.util.ArrayList;

/**
 * To extract task number from mark, unmark and delete command
 */
public class MarkUnmarkDeleteParser {
    /**
     * Parse information for mark, unmark and delete command
     * Only need to check task number given by user
     *
     * @param command information given
     * @throws GrootException if error during check
     */
    protected static void parseMarkUnmarkDelete(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> command) throws GrootException {
        MarkUnmarkDeleteChecker.checkTaskNumberFormat(command.getValue().get(0), command.getKey());
    }

    /**
     * Convert task number from string to integer and check if task number is within range
     *
     * @param taskNumber   task number for operation
     * @param tasklistSize size of tasklist
     * @return task number if valid
     * @throws MarkUnmarkDeleteException.TaskNotFoundException if task number is out of range
     */
    public static int getTaskNumber(String taskNumber, int tasklistSize) throws MarkUnmarkDeleteException.TaskNotFoundException {
        int taskNumberInt = Integer.parseInt(taskNumber);
        MarkUnmarkDeleteChecker.checkTaskNumberValid(taskNumberInt, tasklistSize);
        return taskNumberInt;
    }
}
