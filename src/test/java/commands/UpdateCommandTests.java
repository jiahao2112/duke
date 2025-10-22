package commands;

import enums.CommandType;
import exceptions.UpdateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateCommandTests {
    ArrayList<Task> tasklist = new ArrayList<>(List.of(new ToDo("task1"),
            new Deadline("task2", LocalDateTime.parse(
            "2025-10-13T00:00:00")), new Event("task3", LocalDateTime.parse("2025-10-13T00:00:00"),
            LocalDateTime.parse("2025-10-14T00:00:00"))));
    AbstractMap.SimpleEntry<CommandType, ArrayList<String>> updateInput =
            new AbstractMap.SimpleEntry<>(CommandType.UPDATE, new ArrayList<>());
    @Nested
    @DisplayName("UpdateCommand()")
    class UpdateCommand_Test{

        @Test
        @DisplayName("Success")
        public void UpdateCommand_Success(){
            updateInput.getValue().add("1");
            updateInput.getValue().add("taskName: task4");
            assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));

            updateInput.getValue().clear();

            updateInput.getValue().add("2");
            updateInput.getValue().add("taskName: task4");
            assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));

            updateInput.getValue().clear();

            updateInput.getValue().add("2");
            updateInput.getValue().add("by: 13/10/25 0000");
            assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));

            updateInput.getValue().clear();

            updateInput.getValue().add("2");
            updateInput.getValue().add("taskName: task4");
            updateInput.getValue().add("by: 13/10/25 0000");
            assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));

            updateInput.getValue().clear();

            updateInput.getValue().add("3");
            updateInput.getValue().add("taskName: task4");
            assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));

            updateInput.getValue().clear();

            updateInput.getValue().add("3");
            updateInput.getValue().add("start: 13/10/25 0000");
            assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));

            updateInput.getValue().clear();

            updateInput.getValue().add("3");
            updateInput.getValue().add("end: 13/10/25 0000");
            assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));

            updateInput.getValue().clear();

            updateInput.getValue().add("3");
            updateInput.getValue().add("taskName: task4");
            updateInput.getValue().add("start: 13/10/25 0000");
            assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));

            updateInput.getValue().clear();

            updateInput.getValue().add("3");
            updateInput.getValue().add("taskName: task4");
            updateInput.getValue().add("end: 14/10/25 0000");
            assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));

            updateInput.getValue().clear();

            updateInput.getValue().add("3");
            updateInput.getValue().add("start: 13/10/25 0000");
            updateInput.getValue().add("end: 14/10/25 0000");
            assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));

            updateInput.getValue().clear();

            updateInput.getValue().add("3");
            updateInput.getValue().add("taskName: task4");
            updateInput.getValue().add("start: 13/10/25 0000");
            updateInput.getValue().add("end: 14/10/25 0000");
            assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));
        }

        @Test
        @DisplayName("Missing Update Field Throws InvalidUpdateFieldException")
        public void UpdateCommand_MissingUpdateField_InvalidUpdateFieldException(){
            updateInput.getValue().add("1");
            updateInput.getValue().add(":task4");
            UpdateException updateException = assertThrows(UpdateException.InvalidUpdateTodoFieldException.class,
                    ()->new UpdateCommand(updateInput, tasklist));
            assertEquals("Invalid update field for todo task. Usage: update <task number>, taskName: " +
                            "<update-info>",
                    updateException.getMessage());

            updateInput.getValue().clear();

            updateInput.getValue().add("2");
            updateInput.getValue().add(":task4");
            updateException = assertThrows(UpdateException.InvalidUpdateDeadlineFieldException.class,
                    ()->new UpdateCommand(updateInput, tasklist));
            assertEquals("Invalid update field for deadline task. Usage: update <task number>, taskName: " +
                            "<update-info>, by: <dd/MM/yyyy> <HH:mm (24-hour)>",
                    updateException.getMessage());

            updateInput.getValue().clear();

            updateInput.getValue().add("3");
            updateInput.getValue().add(":task4");
            updateException = assertThrows(UpdateException.InvalidUpdateEventFieldException.class,
                    ()->new UpdateCommand(updateInput, tasklist));
            assertEquals("Invalid update field for event task. Usage: update <task number>, taskName: " +
                            "<update-info>, start: <dd/MM/yyyy> <HH:mm (24-hour)>, end: <dd/MM/yyyy> <HH:mm (24-hour)>",
                    updateException.getMessage());
        }

        @Test
        @DisplayName("Missing Update Info Throws InvalidUpdateInfoException")
        public void UpdateCommand_MissingInfo_MissingUpdateInfoException(){
            updateInput.getValue().add("1");
            updateInput.getValue().add("taskName:");
            UpdateException updateException = assertThrows(UpdateException.InvalidUpdateTodoInfoException.class,
                    ()->new UpdateCommand(updateInput,
                            tasklist));
            assertEquals("Invalid update info for todo task. Usage: update <task number>, taskName: " +
                            "<update-info>",
                    updateException.getMessage());

            updateInput.getValue().clear();

            updateInput.getValue().add("2");
            updateInput.getValue().add("taskName:");
            updateException = assertThrows(UpdateException.InvalidUpdateDeadlineInfoException.class,
                    ()->new UpdateCommand(updateInput,
                            tasklist));
            assertEquals("Invalid update info for deadline task. Usage: update <task number>, taskName: " +
                            "<update-info>, by: <dd/MM/yyyy> <HH:mm (24-hour)>",
                    updateException.getMessage());

            updateInput.getValue().clear();

            updateInput.getValue().add("3");
            updateInput.getValue().add("taskName:");
            updateException = assertThrows(UpdateException.InvalidUpdateEventInfoException.class,
                    ()->new UpdateCommand(updateInput,
                            tasklist));
            assertEquals("Invalid update info for event task. Usage: update <task number>, taskName: " +
                            "<update-info>, start: <dd/MM/yyyy> <HH:mm (24-hour)>, end: <dd/MM/yyyy> <HH:mm (24-hour)>",
                    updateException.getMessage());
        }


        @Test
        @DisplayName("Invalid Date Throws InvalidUpdate<task>InfoException")
        public void UpdateCommand_InvalidDate_InvalidUpdateDateException(){
            updateInput.getValue().add("2");
            updateInput.getValue().add("by: 13/10/25");
            UpdateException updateException = assertThrows(UpdateException.InvalidUpdateDeadlineDateException.class,
                    ()->new UpdateCommand(updateInput,
                            tasklist));
            assertEquals("Invalid date for deadline task. Usage: update <task number>, taskName: " +
                            "<update-info>, by: <dd/MM/yyyy> <HH:mm (24-hour)>",
                    updateException.getMessage());

            updateInput.getValue().clear();

            updateInput.getValue().add("3");
            updateInput.getValue().add("start: 13/10/25");
            updateException = assertThrows(UpdateException.InvalidUpdateEventDateException.class,
                    ()->new UpdateCommand(updateInput,
                            tasklist));
            assertEquals("Invalid date for event task. Usage: update <task number>, taskName: " +
                            "<update-info>, start: <dd/MM/yyyy> <HH:mm (24-hour)>, end: <dd/MM/yyyy> <HH:mm (24-hour)>",
                    updateException.getMessage());
        }
    }

    @Nested
    @DisplayName("execute()")
    class ExecuteTest {
        @Test
        @DisplayName("Update Todo Success")
        public void execute_Success(){
            updateInput.getValue().add("1"); //Todo task
            updateInput.getValue().add("taskName: task4");
            Command command = assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));
            assertDoesNotThrow(command::execute);
            assertEquals("task4", tasklist.get(0).getDescription());

            updateInput.getValue().clear();

            updateInput.getValue().add("2"); //Deadline task
            updateInput.getValue().add("taskName: task5");
            command = assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));
            assertDoesNotThrow(command::execute);
            assertEquals("task5", tasklist.get(1).getDescription());

            updateInput.getValue().clear();

            updateInput.getValue().add("2"); //Deadline task
            updateInput.getValue().add("by: 21/10/25 0800");
            command = assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));
            assertDoesNotThrow(command::execute);
            assertEquals(LocalDateTime.parse("2025-10-21T08:00:00"), ((Deadline)tasklist.get(1)).getBy());

            updateInput.getValue().clear();

            updateInput.getValue().add("2"); //Deadline task
            updateInput.getValue().add("taskName: task5");
            updateInput.getValue().add("by: 21/10/25 0800");
            command = assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));
            assertDoesNotThrow(command::execute);
            assertEquals("task5",  tasklist.get(1).getDescription());
            assertEquals(LocalDateTime.parse("2025-10-21T08:00:00"), ((Deadline)tasklist.get(1)).getBy());

            updateInput.getValue().clear();

            updateInput.getValue().add("3"); //Event task
            updateInput.getValue().add("taskName: task6");
            command = assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));
            assertDoesNotThrow(command::execute);
            assertEquals("task6", tasklist.get(2).getDescription());

            updateInput.getValue().clear();

            updateInput.getValue().add("3"); //Event task
            updateInput.getValue().add("start: 21/10/25 0800");
            command = assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));
            assertDoesNotThrow(command::execute);
            assertEquals(LocalDateTime.parse("2025-10-21T08:00:00"), ((Event)tasklist.get(2)).getStartDateTime());

            updateInput.getValue().clear();

            updateInput.getValue().add("3"); //Event task
            updateInput.getValue().add("end: 21/10/25 0800");
            command = assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));
            assertDoesNotThrow(command::execute);
            assertEquals(LocalDateTime.parse("2025-10-21T08:00:00"), ((Event)tasklist.get(2)).getEndDateTime());

            updateInput.getValue().clear();

            updateInput.getValue().add("3"); //Event task
            updateInput.getValue().add("taskName: task6");
            updateInput.getValue().add("start: 21/10/25 0800");
            command = assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));
            assertDoesNotThrow(command::execute);
            assertEquals("task6",  tasklist.get(2).getDescription());
            assertEquals(LocalDateTime.parse("2025-10-21T08:00:00"), ((Event)tasklist.get(2)).getStartDateTime());

            updateInput.getValue().clear();

            updateInput.getValue().add("3"); //Event task
            updateInput.getValue().add("taskName: task6");
            updateInput.getValue().add("end: 21/10/25 0800");
            command = assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));
            assertDoesNotThrow(command::execute);
            assertEquals("task6",  tasklist.get(2).getDescription());
            assertEquals(LocalDateTime.parse("2025-10-21T08:00:00"), ((Event)tasklist.get(2)).getEndDateTime());

            updateInput.getValue().clear();

            updateInput.getValue().add("3"); //Event task
            updateInput.getValue().add("start: 21/10/25 0000");
            updateInput.getValue().add("end: 21/10/25 0800");
            command = assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));
            assertDoesNotThrow(command::execute);
            assertEquals(LocalDateTime.parse("2025-10-21T00:00:00"), ((Event)tasklist.get(2)).getStartDateTime());
            assertEquals(LocalDateTime.parse("2025-10-21T08:00:00"), ((Event)tasklist.get(2)).getEndDateTime());

            updateInput.getValue().clear();

            updateInput.getValue().add("3"); //Event task
            updateInput.getValue().add("taskName: task6");
            updateInput.getValue().add("start: 21/10/25 0000");
            updateInput.getValue().add("end: 21/10/25 0800");
            command = assertDoesNotThrow(()->new UpdateCommand(updateInput, tasklist));
            assertDoesNotThrow(command::execute);
            assertEquals("task6",  tasklist.get(2).getDescription());
            assertEquals(LocalDateTime.parse("2025-10-21T00:00:00"), ((Event)tasklist.get(2)).getStartDateTime());
            assertEquals(LocalDateTime.parse("2025-10-21T08:00:00"), ((Event)tasklist.get(2)).getEndDateTime());
        }
    }
}
