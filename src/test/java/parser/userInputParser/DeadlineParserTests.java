package parser.userInputParser;

import enums.CommandType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineParserTests {
    AbstractMap.SimpleEntry<CommandType, ArrayList<String>> deadline = new AbstractMap.SimpleEntry<>(CommandType.DEADLINE, new ArrayList<>());

    @Nested
    @DisplayName("parseDeadline()")
    class ParseDeadlineTests {
        @Test
        @DisplayName("Success")
        public void parseDeadline_Success(){
            deadline.getValue().add("task /by 13/10/25 0000");
            assertDoesNotThrow(()-> DeadlineParser.parseDeadline(deadline));
            AbstractMap.SimpleEntry<CommandType, ArrayList<String>> deadlineCheck =
                    new AbstractMap.SimpleEntry<>(CommandType.DEADLINE, new ArrayList<>());
            deadlineCheck.getValue().add("task");
            deadlineCheck.getValue().add("13/10/25 0000");
            assertEquals(deadline, deadlineCheck);
        }
    }
}
