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
    class MarkTaskCommandTest {
        @Test
        @DisplayName("Mark Success")
        public void MarkTaskCommandTest_Mark_Success() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.MARK, new ArrayList<>());
            commandLine.getValue().add("1");
            assertDoesNotThrow(()->new MarkTaskCommand(commandLine,  tasklist));
        }
        @Test
        @DisplayName("Unmark Success")
        public void MarkTaskCommandTest_Unmark_Success() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.UNMARK, new ArrayList<>());
            commandLine.getValue().add("1");
            tasklist.get(0).setIsDone(true);
            assertDoesNotThrow(()->new MarkTaskCommand(commandLine,  tasklist));
        }

        @Test
        @DisplayName("Throws TaskNotFoundException")
        public void MarkTaskCommandTest_Mark_TaskNotFound() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.MARK, new ArrayList<>());
            commandLine.getValue().add("2");
            MarkUnmarkDeleteException markUnmarkDeleteException = assertThrows(MarkUnmarkDeleteException.TaskNotFoundException.class,
                    ()->new MarkTaskCommand(commandLine,  tasklist));
            assertEquals("Task not found in task list.", markUnmarkDeleteException.getMessage());
        }

        @Test
        @DisplayName("Throws TaskAlreadyMarkedException, Task already marked as done")
        public void MarkTaskCommandTest_Mark_TaskAlreadyMarked() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.MARK, new ArrayList<>());
            commandLine.getValue().add("1");
            tasklist.get(0).setIsDone(true);
            MarkUnmarkDeleteException markUnmarkDeleteException = assertThrows(MarkUnmarkDeleteException.TaskAlreadyMarkedException.class, ()-> new MarkTaskCommand(commandLine,  tasklist));
            assertEquals("Task is already marked in task list as done.", markUnmarkDeleteException.getMessage());
        }

        @Test
        @DisplayName("Throws TaskAlreadyMarkedException, Task already marked as not done")
        public void MarkTaskCommandTest_Unmark_TaskAlreadyMarked() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.UNMARK, new ArrayList<>());
            commandLine.getValue().add("1");
            MarkUnmarkDeleteException markUnmarkDeleteException = assertThrows(MarkUnmarkDeleteException.TaskAlreadyMarkedException.class, ()-> new MarkTaskCommand(commandLine,  tasklist));
            assertEquals("Task is already marked in task list as not done.", markUnmarkDeleteException.getMessage());
        }
    }

    @Nested
    @DisplayName("execute()")
    public class ExecuteTest {
        @Test
        @DisplayName("Mark Success")
        public void MarkTaskCommandTest_Mark_Success() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.MARK, new ArrayList<>());
            commandLine.getValue().add("1");
            MarkTaskCommand markTaskCommand = assertDoesNotThrow(()->new MarkTaskCommand(commandLine,  tasklist));
            markTaskCommand.execute();
            assertTrue(tasklist.get(0).getIsDone());
        }

        @Test
        @DisplayName("Unmark Success")
        public void MarkTaskCommandTest_Unmark_Success() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.UNMARK, new ArrayList<>());
            commandLine.getValue().add("1");
            tasklist.get(0).setIsDone(true);
            MarkTaskCommand markTaskCommand = assertDoesNotThrow(()->new MarkTaskCommand(commandLine,  tasklist));
            markTaskCommand.execute();
            assertFalse(tasklist.get(0).getIsDone());
        }
    }
}
