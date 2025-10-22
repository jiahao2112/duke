package commands;

import enums.CommandType;
import exceptions.MarkUnmarkDeleteException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.ToDo;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MarkTaskCommandTests {
    AbstractMap.SimpleEntry<CommandType, ArrayList<String>> commandLine;
    ArrayList<Task> tasklist = new ArrayList<>(List.of(new ToDo("task")));

    @Nested
    @DisplayName("MarkTaskCommand()")
    class MarkTaskCommand_Test {
        @Test
        @DisplayName("Mark Success")
        public void MarkTaskCommand_Mark_Success() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.MARK, new ArrayList<>());
            commandLine.getValue().add("1");
            assertDoesNotThrow(() -> new MarkTaskCommand(commandLine, tasklist));
            assertTrue(tasklist.get(0).getIsDone());

            commandLine = new AbstractMap.SimpleEntry<>(CommandType.UNMARK, new ArrayList<>());
            commandLine.getValue().add("1");
            assertDoesNotThrow(() -> new MarkTaskCommand(commandLine, tasklist));
        }

        @Test
        @DisplayName("Throws TaskNotFoundException")
        public void MarkTaskCommand_TaskNotInRange_TaskNotFoundException() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.MARK, new ArrayList<>());
            commandLine.getValue().add("2");
            MarkUnmarkDeleteException markUnmarkDeleteException =
                    assertThrows(MarkUnmarkDeleteException.TaskNotFoundException.class,
                            () -> new MarkTaskCommand(commandLine, tasklist));
            assertEquals("Task not found in task list.", markUnmarkDeleteException.getMessage());

            commandLine.getValue().set(0, "0");
            markUnmarkDeleteException = assertThrows(MarkUnmarkDeleteException.TaskNotFoundException.class,
                    () -> new MarkTaskCommand(commandLine, tasklist));
            assertEquals("Task not found in task list.", markUnmarkDeleteException.getMessage());
        }

        @Test
        @DisplayName("Throws TaskAlreadyMarkedException")
        public void MarkTaskCommand_TaskAlreadyMarked_TaskAlreadyMarkedException() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.MARK, new ArrayList<>());
            commandLine.getValue().add("1");
            tasklist.get(0).setIsDone(true);
            MarkUnmarkDeleteException markUnmarkDeleteException =
                    assertThrows(MarkUnmarkDeleteException.TaskAlreadyMarkedException.class,
                            () -> new MarkTaskCommand(commandLine, tasklist));
            assertEquals("Task is already marked in task list as done.", markUnmarkDeleteException.getMessage());

            commandLine = new AbstractMap.SimpleEntry<>(CommandType.UNMARK, new ArrayList<>());
            commandLine.getValue().add("1");
            tasklist.get(0).setIsDone(false);
            markUnmarkDeleteException = assertThrows(MarkUnmarkDeleteException.TaskAlreadyMarkedException.class,
                    () -> new MarkTaskCommand(commandLine, tasklist));
            assertEquals("Task is already marked in task list as not done.", markUnmarkDeleteException.getMessage());
        }
    }

    @Nested
    @DisplayName("execute()")
    public class Execute_Test {
        @Test
        @DisplayName("Success")
        public void execute_Success() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.MARK, new ArrayList<>());
            commandLine.getValue().add("1");
            MarkTaskCommand markTaskCommand = assertDoesNotThrow(() -> new MarkTaskCommand(commandLine, tasklist));
            markTaskCommand.execute();
            assertTrue(tasklist.get(0).getIsDone());

            commandLine = new AbstractMap.SimpleEntry<>(CommandType.UNMARK, new ArrayList<>());
            commandLine.getValue().add("1");
            markTaskCommand = assertDoesNotThrow(() -> new MarkTaskCommand(commandLine, tasklist));
            markTaskCommand.execute();
            assertFalse(tasklist.get(0).getIsDone());
        }
    }
}
