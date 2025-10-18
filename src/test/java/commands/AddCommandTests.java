package commands;

import enums.CommandType;
import exceptions.DateTimeException;
import exceptions.FileException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

import java.util.AbstractMap;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AddCommandTests {
    AbstractMap.SimpleEntry<CommandType, ArrayList<String>> commandLine;
    ArrayList<Task> tasklist = new ArrayList<>();

    @Nested
    @DisplayName("AddCommand()")
    class AddCommandTest {
        @Test
        @DisplayName("Todo Success")
        public void AddCommandTest_TodoSuccess() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.TODO, new ArrayList<>());
            commandLine.getValue().add("task");
            assertDoesNotThrow(() -> new AddCommand(commandLine, tasklist));
        }

        @Test
        @DisplayName("Deadline Success")
        public void AddCommandTest_DeadlineSuccess() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.DEADLINE, new ArrayList<>());
            commandLine.getValue().add("task");
            commandLine.getValue().add("13/10/25 0000");
            assertDoesNotThrow(() -> new AddCommand(commandLine, tasklist));
        }

        @Test
        @DisplayName("Event Success")
        public void AddCommandTest_EventSuccess() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.EVENT, new ArrayList<>());
            commandLine.getValue().add("task");
            commandLine.getValue().add("13/10/25 00:00");
            commandLine.getValue().add("13/10/25 1200");
            assertDoesNotThrow(() -> new AddCommand(commandLine, tasklist));
        }

        @Test
        @DisplayName("Throws DateTimeException")
        public void AddCommandTest_DateTime() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.EVENT, new ArrayList<>());
            commandLine.getValue().add("task");
            commandLine.getValue().add("13/10/25");
            commandLine.getValue().add("13/10/25 1200");
            DateTimeException dateTimeException = assertThrows(DateTimeException.class, () -> new AddCommand(commandLine, tasklist));
            assertEquals("", dateTimeException.getMessage());
        }
    }

    @Nested
    @DisplayName("createTask()")
    class CreateTaskTest {
        @Test
        @DisplayName("Todo Success")
        public void CreateTaskTest_Todo_Success() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.TODO, new ArrayList<>());
            commandLine.getValue().add("task");
            assertDoesNotThrow(() -> AddCommand.createTask(commandLine));
        }

        @Test
        @DisplayName("Deadline Success")
        public void CreateTaskTest_Deadline_Success() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.DEADLINE, new ArrayList<>());
            commandLine.getValue().add("task");
            commandLine.getValue().add("13/10/25 0000");
            assertDoesNotThrow(() -> AddCommand.createTask(commandLine));
        }

        @Test
        @DisplayName("Event Success")
        public void CreateTaskTest_Event_Success() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.EVENT, new ArrayList<>());
            commandLine.getValue().add("task");
            commandLine.getValue().add("13/10/25 0000");
            commandLine.getValue().add("13/10/25 1200");
            assertDoesNotThrow(() -> AddCommand.createTask(commandLine));
        }

        @Test
        @DisplayName("Throws FileCorruptedException")
        public void CreateTaskTest_FileCorrupted() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.TODO, new ArrayList<>());
            FileException fileException = assertThrows(FileException.FileCorruptedException.class,
                    () -> AddCommand.createTask(commandLine));
            assertEquals("Tasklist file is corrupted.", fileException.getMessage());

            commandLine = new AbstractMap.SimpleEntry<>(CommandType.DEADLINE, new ArrayList<>());
            fileException = assertThrows(FileException.FileCorruptedException.class,
                    () -> AddCommand.createTask(commandLine));
            assertEquals("Tasklist file is corrupted.", fileException.getMessage());

            commandLine = new AbstractMap.SimpleEntry<>(CommandType.EVENT, new ArrayList<>());
            fileException = assertThrows(FileException.FileCorruptedException.class,
                    () -> AddCommand.createTask(commandLine));
            assertEquals("Tasklist file is corrupted.", fileException.getMessage());

            commandLine = new AbstractMap.SimpleEntry<>(CommandType.DEADLINE, new ArrayList<>());
            commandLine.getValue().add("task");
            commandLine.getValue().add("13/10/25");
            fileException = assertThrows(FileException.FileCorruptedException.class,
                    () -> AddCommand.createTask(commandLine));
            assertEquals("Tasklist file is corrupted.", fileException.getMessage());

            commandLine = new AbstractMap.SimpleEntry<>(CommandType.EVENT, new ArrayList<>());
            commandLine.getValue().add("task");
            commandLine.getValue().add("13/10/25");
            commandLine.getValue().add("13/10/25");
            fileException = assertThrows(FileException.FileCorruptedException.class,
                    () -> AddCommand.createTask(commandLine));
            assertEquals("Tasklist file is corrupted.", fileException.getMessage());
        }
    }

    @Nested
    @DisplayName("execute()")
    class AddTaskTest {
        @Test
        @DisplayName("Add Todo Task")
        public void AddTaskTest_Todo() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.TODO, new ArrayList<>());
            commandLine.getValue().add("task");
            AddCommand addCommand = assertDoesNotThrow(() -> new AddCommand(commandLine, tasklist));
            addCommand.execute();
            assertEquals(1, tasklist.size());
            assertInstanceOf(ToDo.class, tasklist.get(0));
        }

        @Test
        @DisplayName("Add Deadline Task")
        public void AddTaskTest_Deadline() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.DEADLINE, new ArrayList<>());
            commandLine.getValue().add("task");
            commandLine.getValue().add("13/10/25 0000");
            AddCommand addCommand = assertDoesNotThrow(() -> new AddCommand(commandLine, tasklist));
            addCommand.execute();
            assertEquals(1, tasklist.size());
            assertInstanceOf(Deadline.class, tasklist.get(0));
        }

        @Test
        @DisplayName("Add Event Task")
        public void AddTaskTest_Event() {
            commandLine = new AbstractMap.SimpleEntry<>(CommandType.EVENT, new ArrayList<>());
            commandLine.getValue().add("task");
            commandLine.getValue().add("13/10/25 0000");
            commandLine.getValue().add("13/10/25 1200");
            AddCommand addCommand = assertDoesNotThrow(() -> new AddCommand(commandLine, tasklist));
            addCommand.execute();
            assertEquals(1, tasklist.size());
            assertInstanceOf(Event.class, tasklist.get(0));
        }
    }

}
