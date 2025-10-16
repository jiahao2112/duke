package commands;

import enums.CommandType;
import exceptions.FileException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tasks.Task;

import java.util.AbstractMap;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ExitCommandTests {
    ArrayList<Task> tasklist = new ArrayList<>();

    @Nested
    @DisplayName("ExitCommand()")
    public class ExitCommandTest {
        @Test
        @DisplayName("Success")
        public void exitCommandTest_Success(){
            assertDoesNotThrow(()->new ExitCommand(tasklist));
        }
    }
}
