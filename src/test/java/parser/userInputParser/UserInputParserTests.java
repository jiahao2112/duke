package parser.userInputParser;

import enums.CommandType;
import exceptions.GrootException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserInputParserTests {
    @Nested
    @DisplayName("parseUserInput()")
    class ParseUserInputTests {
        @Test
        @DisplayName("todo Success")
        public void parseUserInput_Todo_Success() {
            String input = "todo task1";
            AbstractMap.SimpleEntry<CommandType, ArrayList<String>> result =
                    assertDoesNotThrow(()->UserInputParser.parseUserInput(input));
            assertEquals(CommandType.TODO, result.getKey());
            assertEquals("task1", result.getValue().get(0));
        }

        @Test
        @DisplayName("deadline Success")
        public void parseUserInput_Deadline_Success() {
            String input = "deadline task1 /by 13/10/25 0000";
            AbstractMap.SimpleEntry<CommandType, ArrayList<String>> result =
                    assertDoesNotThrow(()->UserInputParser.parseUserInput(input));
            assertEquals(CommandType.DEADLINE, result.getKey());
            assertEquals("task1", result.getValue().get(0));
            assertEquals("13/10/25 0000", result.getValue().get(1));
        }

        @Test
        @DisplayName("event Success")
        public void parseUserInput_Event_Success() {
            String input = "event task1 /from 13/10/25 0000 /to 14/10/25 0000";
            AbstractMap.SimpleEntry<CommandType, ArrayList<String>> result =
                    assertDoesNotThrow(()->UserInputParser.parseUserInput(input));
            assertEquals(CommandType.EVENT, result.getKey());
            assertEquals("task1", result.getValue().get(0));
            assertEquals("13/10/25 0000", result.getValue().get(1));
            assertEquals("14/10/25 0000", result.getValue().get(2));
        }

        @Test
        @DisplayName("list Success")
        public void parseUserInput_List_Success() {
            String input = "list";
            AbstractMap.SimpleEntry<CommandType, ArrayList<String>> result =
                    assertDoesNotThrow(()->UserInputParser.parseUserInput(input));
            assertEquals(CommandType.LIST, result.getKey());
        }

        @Test
        @DisplayName("view Success")
        public void parseUserInput_View_Success() {
            String input = "view 13/10/25";
            AbstractMap.SimpleEntry<CommandType, ArrayList<String>> result =
                    assertDoesNotThrow(()->UserInputParser.parseUserInput(input));
            assertEquals(CommandType.VIEW, result.getKey());
            assertEquals("13/10/25", result.getValue().get(0));
        }

        @Test
        @DisplayName("noInput Success")
        public void parseUserInput_None_Success() {
            String input = "";
            AbstractMap.SimpleEntry<CommandType, ArrayList<String>> result =
                    assertDoesNotThrow(()->UserInputParser.parseUserInput(input));
            assertEquals(CommandType.NONE, result.getKey());
        }

        @Test
        @DisplayName("mark Success")
        public void parseUserInput_Mark_Success() {
            String input = "mark 1";
            AbstractMap.SimpleEntry<CommandType, ArrayList<String>> result =
                    assertDoesNotThrow(()->UserInputParser.parseUserInput(input));
            assertEquals(CommandType.MARK, result.getKey());
            assertEquals("1", result.getValue().get(0));
        }

        @Test
        @DisplayName("unmark Success")
        public void parseUserInput_Unmark_Success() {
            String input = "unmark 1";
            AbstractMap.SimpleEntry<CommandType, ArrayList<String>> result =
                    assertDoesNotThrow(()->UserInputParser.parseUserInput(input));
            assertEquals(CommandType.UNMARK, result.getKey());
            assertEquals("1", result.getValue().get(0));
        }

        @Test
        @DisplayName("delete Success")
        public void parseUserInput_Delete_Success() {
            String input = "delete 1";
            AbstractMap.SimpleEntry<CommandType, ArrayList<String>> result =
                    assertDoesNotThrow(()->UserInputParser.parseUserInput(input));
            assertEquals(CommandType.DELETE, result.getKey());
            assertEquals("1", result.getValue().get(0));
        }

        @Test
        @DisplayName("Throws InvalidCommandException")
        public void parseUserInput_InvalidCommand() {
            String input = "invalid";
            GrootException grootException = assertThrows(GrootException.InvalidCommandException.class,
                    ()->UserInputParser.parseUserInput(input));
            assertEquals("Invalid command: invalid", grootException.getMessage());
        }
    }
}
