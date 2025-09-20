package parser.userInputParser;

import checker.TodoChecker;
import enums.CommandType;
import exceptions.GrootException;

import java.util.AbstractMap;
import java.util.ArrayList;

public class TodoParser {
    protected static void parseTodo(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> input) throws GrootException {
        TodoChecker.checkTodoTaskName(input.getValue().get(0));
    }
}
