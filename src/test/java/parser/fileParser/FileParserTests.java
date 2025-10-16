package parser.fileParser;

import exceptions.FileException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FileParserTests {
    FileParser fileParser = new FileParser();
    @Nested
    @DisplayName("parseTaskFileTests")
    class ParserTaskFileTest {
        @Test
        @DisplayName("Todo Success")
        void parseTaskFile_Todo_Success() {
            String task = "[T][X] task1";
            Task taskCreated = assertDoesNotThrow(()-> fileParser.parseTaskFile(task));
            assertInstanceOf(ToDo.class, taskCreated);
            assertTrue(taskCreated.getIsDone());
            assertEquals("task1", taskCreated.getDescription());
        }

        @Test
        @DisplayName("Deadline Success")
        void parseTaskFile_Deadline_Success() {
            String task = "[D][ ] task1 | by: 13/10/25 0000";
            Task taskCreated = assertDoesNotThrow(()-> fileParser.parseTaskFile(task));
            assertInstanceOf(Deadline.class, taskCreated);
            assertFalse(taskCreated.getIsDone());
            assertEquals("task1", taskCreated.getDescription());
            assertEquals(LocalDateTime.parse("2025-10-13T00:00:00"), ((Deadline)taskCreated).getBy());
        }

        @Test
        @DisplayName("Event Success")
        void parseTaskFile_Event_Success() {
            String task = "[E][ ] task1 | from: 13/10/25 0000, to: 14/10/25 0000";
            Task taskCreated = assertDoesNotThrow(()-> fileParser.parseTaskFile(task));
            assertInstanceOf(Event.class, taskCreated);
            assertFalse(taskCreated.getIsDone());
            assertEquals("task1", taskCreated.getDescription());
            assertEquals(LocalDateTime.parse("2025-10-13T00:00:00"), ((Event)taskCreated).getStartDateTime());
            assertEquals(LocalDateTime.parse("2025-10-14T00:00:00"), ((Event)taskCreated).getEndDateTime());
        }

        @Test
        @DisplayName("Invalid Task Type Throws FileCorruptedException")
        void parseTaskFile_InvalidTaskType_FileCorrupted() {
            String task = "[A][X] task1";
            assertThrows(FileException.FileCorruptedException.class, ()-> fileParser.parseTaskFile(task));
        }

        @Test
        @DisplayName("Invalid Task Done Throws FileCorruptedException")
        void parseTaskFile_InvalidTaskDone_FileCorrupted() {
            String task = "[T][A] task1";
            assertThrows(FileException.FileCorruptedException.class, ()-> fileParser.parseTaskFile(task));
        }
    }
}
