package parser.userInputParser;

import enums.CommandType;
import exceptions.GrootException;

import java.util.AbstractMap;
import java.util.ArrayList;

/**
 * Main parsing used for user input
 */
public class UserInputParser {
    /**
     * Get the relevant information
     *
     * @param input user input
     * @return a pair where the key is the command and value are the information for the command
     * @throws GrootException if there are any error during parsing
     */
    public static AbstractMap.SimpleEntry<CommandType, ArrayList<String>> parseUserInput(String input) throws GrootException {
        AbstractMap.SimpleEntry<CommandType, ArrayList<String>> inputs = splitInput(input); //split and trim the 2 parts of command {command, info}
        switch (inputs.getKey()) {
            case NONE:
            case LIST:
                break; //no additional information to parse
            case MARK:
            case UNMARK:
            case DELETE:
                MarkUnmarkDeleteParser.parseMarkUnmarkDelete(inputs); // check if task number available and is digit
                break;
            case TODO:
                TodoParser.parseTodo(inputs); // add task name to userInput {"taskName"}
                break;
            case DEADLINE:
                DeadlineParser.parseDeadline(inputs); //add additional parsing to userInput {"taskName", "by"}
                break;
            case EVENT:
                EventParser.parseEvent(inputs); //add additional parsing to userInput {"taskName", "from", "by"}
                break;
            default:
        }
        return inputs;
    }

    /*
     * Convert command from string to CommandType enum
     */
    private static CommandType parseCommand(String command) throws GrootException.InvalidCommandException {
        try {
            command = command.trim();
            return CommandType.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new GrootException.InvalidCommandException(command);
        }
    }

    /*
     * Split user input into 2 parts, command and information
     */
    private static AbstractMap.SimpleEntry<CommandType, ArrayList<String>> splitInput(String command) throws GrootException {
        String[] commandSplit = command.split(" ", 2);
        ArrayList<String> taskInfo = new ArrayList<>();
        CommandType commandType;
        commandType = parseCommand(commandSplit[0]);
        if (commandSplit.length < 2) { //only one word
            taskInfo.add("");
        } else {
            taskInfo.add(commandSplit[1].trim());
        }
        return new AbstractMap.SimpleEntry<>(commandType, taskInfo);
    }
}
