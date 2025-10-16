package parser.userInputParser;

import enums.CommandType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.ToDo;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarkUnmarkDeleteParserTests {
    @Nested
    @DisplayName("parseMarkUnmarkDelete()")
    class ParseMarkUnmarkDeleteTest{
        AbstractMap.SimpleEntry<CommandType, ArrayList<String>> mark = new AbstractMap.SimpleEntry<>(CommandType.MARK,
                new ArrayList<>(List.of("1")));
        @Test
        @DisplayName("Success")
        public void parseMarkUnmarkDelete_Success(){
            assertDoesNotThrow(()-> MarkUnmarkDeleteParser.parseMarkUnmarkDelete(mark));
        }
    }

    @Nested
    @DisplayName("getTaskNumber()")
    class GetTaskNumberTest{
        @Test
        @DisplayName("Success")
        public void getTaskNumber_Success(){
            int taskNumber = assertDoesNotThrow(()->MarkUnmarkDeleteParser.getTaskNumber("1", 2));
            assertEquals(1, taskNumber);
        }
    }
}
