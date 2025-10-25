package parser.userInputParser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarkUnmarkDeleteParserTests {

    @Nested
    @DisplayName("getTaskNumber()")
    class GetTaskNumber_Test {
        @Test
        @DisplayName("Success")
        public void getTaskNumber_Success() {
            int taskNumber = assertDoesNotThrow(() -> TaskNumberParser.getTaskNumber("1", 2));

            assertEquals(1, taskNumber);
        }
    }
}
