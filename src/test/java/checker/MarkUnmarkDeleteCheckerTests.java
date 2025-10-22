package checker;

import enums.CommandType;
import exceptions.MarkUnmarkDeleteException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MarkUnmarkDeleteCheckerTests {
    @Nested
    @DisplayName("checkTaskNumberFormat()")
    class CheckTaskNumberFormat_Test {
        String taskNumber;

        @Test
        @DisplayName("Success")
        public void checkTaskNumberFormat_Success() {
            taskNumber = "1";
            assertDoesNotThrow(() -> MarkUnmarkDeleteChecker.checkTaskNumberFormat(taskNumber, CommandType.MARK));
        }

        @Test
        @DisplayName("Throws MissingTaskNumberException")
        public void checkTaskNumberFormat_MissingTaskNumber_MissingTaskNumberException() {
            taskNumber = "";
            MarkUnmarkDeleteException markUnmarkDeleteException =
                    assertThrows(MarkUnmarkDeleteException.MissingTaskNumberException.class,
                    () -> MarkUnmarkDeleteChecker.checkTaskNumberFormat(taskNumber, CommandType.MARK));
            assertEquals("Missing task number. Usage: mark <task number>", markUnmarkDeleteException.getMessage());
        }

        @Test
        @DisplayName("Throws InvalidTaskNumberException")
        public void checkTaskNumberFormat_NotDigit_InvalidTaskNumberException() {
            taskNumber = "a";
            MarkUnmarkDeleteException markUnmarkDeleteException =
                    assertThrows(MarkUnmarkDeleteException.InvalidTaskNumberException.class,
                    () -> MarkUnmarkDeleteChecker.checkTaskNumberFormat(taskNumber, CommandType.MARK));
            assertEquals("Invalid task number. Usage: mark <task number>", markUnmarkDeleteException.getMessage());
        }
    }

    @Nested
    @DisplayName("checkTaskNumberValid()")
    class CheckTaskNumberValid_Test {
        int taskNumber;
        final int taskListSize = 2;

        @Test
        @DisplayName("Success ->")
        public void checkTaskNumberValid_Success() {
            taskNumber = 1;
            assertDoesNotThrow(() -> MarkUnmarkDeleteChecker.checkTaskNumberValid(taskNumber, taskListSize));
        }

        @Test
        @DisplayName("Throws TaskNotFoundException")
        public void checkTaskNumberFormat_TaskNumberNotInRange_TaskNotFoundException() {
            taskNumber = 3; //more than task list size
            MarkUnmarkDeleteException markUnmarkDeleteException =
                    assertThrows(MarkUnmarkDeleteException.TaskNotFoundException.class,
                    () -> MarkUnmarkDeleteChecker.checkTaskNumberValid(taskNumber, taskListSize));
            assertEquals("Task not found in task list.", markUnmarkDeleteException.getMessage());

            taskNumber = 0; //task number cannot be less than 1
            markUnmarkDeleteException = assertThrows(MarkUnmarkDeleteException.TaskNotFoundException.class,
                    () -> MarkUnmarkDeleteChecker.checkTaskNumberValid(taskNumber, taskListSize));
            assertEquals("Task not found in task list.", markUnmarkDeleteException.getMessage());
        }
    }

    @Nested
    class CheckTaskStatusValid_Test {
        @Test
        @DisplayName("Success")
        public void checkTaskStatusValid_Success() {
            assertDoesNotThrow(() -> MarkUnmarkDeleteChecker.checkTaskStatus(true, false));
            assertDoesNotThrow(() -> MarkUnmarkDeleteChecker.checkTaskStatus(false, true));
        }

        @Test
        @DisplayName("Throws TaskAlreadyMarkedException")
        public void checkTaskStatusValid_TaskAlreadyMarked_TaskAlreadyMarkedException() {
            MarkUnmarkDeleteException markUnmarkDeleteException =
                    assertThrows(MarkUnmarkDeleteException.TaskAlreadyMarkedException.class,
                    () -> MarkUnmarkDeleteChecker.checkTaskStatus(true, true));
            assertEquals("Task is already marked in task list as done.", markUnmarkDeleteException.getMessage());

            markUnmarkDeleteException = assertThrows(MarkUnmarkDeleteException.TaskAlreadyMarkedException.class,
                    () -> MarkUnmarkDeleteChecker.checkTaskStatus(false, false));
            assertEquals("Task is already marked in task list as not done.", markUnmarkDeleteException.getMessage());
        }
    }
}
