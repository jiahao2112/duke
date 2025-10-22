package parser.userInputParser;

import checker.FindChecker;
import enums.CommandType;
import exceptions.FindException;

import java.util.AbstractMap;
import java.util.ArrayList;

public class FindParser {
    public static void parseFind (AbstractMap.SimpleEntry<CommandType, ArrayList<String>> input) throws FindException {
        FindChecker.checkKeyword(input.getValue().get(0));
    }
}
