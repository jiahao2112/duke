package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTests {
    @TempDir
    File tempDir; // JUnit temporary directory

    File testFolder;
    File testFile;

    @BeforeEach
    void setup() throws IOException {
        testFolder = new File(tempDir, "data");
        assertTrue(testFolder.mkdirs());
        testFile = new File(testFolder, "tasklist.txt");
        assertTrue(testFile.createNewFile());

        FileManager.setFolderAndFile(testFolder, testFile);

        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("[T][X] task1\n");
            writer.write("[D][ ] task2 | by: 13 Oct 2025 0000\n");
            writer.write("[E][ ] task3 | from: 13 Oct 2025 0000, to: 14 Oct 2025 0000\n");
        }
    }

    @Nested
    @DisplayName("TaskManager()")
    class taskManagerTest{
        @Test
        @DisplayName("Success")
        public void taskManagerTest_Success(){
            assertDoesNotThrow(TaskManager::new);

            assertEquals(3, TaskManager.getTasklist().size());

            ArrayList<Task> tasks = TaskManager.getTasklist();
            assertInstanceOf(ToDo.class, tasks.get(0));
            assertEquals("task1", tasks.get(0).getDescription());
            assertTrue(tasks.get(0).getIsDone());

            assertInstanceOf(Deadline.class, tasks.get(1));
            assertEquals("task2", tasks.get(1).getDescription());
            assertFalse(tasks.get(1).getIsDone());
            assertEquals(LocalDateTime.parse("2025-10-13T00:00:00"), ((Deadline)tasks.get(1)).getBy());

            assertInstanceOf(Event.class, tasks.get(2));
            assertEquals("task3", tasks.get(2).getDescription());
            assertFalse(tasks.get(2).getIsDone());
            assertEquals(LocalDateTime.parse("2025-10-13T00:00:00"), ((Event)tasks.get(2)).getStartDateTime());
            assertEquals(LocalDateTime.parse("2025-10-14T00:00:00"), ((Event)tasks.get(2)).getEndDateTime());
        }
    }

    @Nested
    @DisplayName("manageTask()")
    class ManageTaskTest{
        @Test
        @DisplayName("Success")
        public void manageTaskTest_Success(){
            String userInput = "todo task4";
            TaskManager taskManager = assertDoesNotThrow(TaskManager::new);
            assertEquals(3, TaskManager.getTasklist().size());

            assertDoesNotThrow(()->taskManager.manageTask(userInput));
            assertEquals(4, TaskManager.getTasklist().size());

            assertInstanceOf(ToDo.class, TaskManager.getTasklist().get(3));
            assertEquals("task4", TaskManager.getTasklist().get(3).getDescription());

            List<String> lines = assertDoesNotThrow(() -> Files.readAllLines(testFile.toPath()));
            assertEquals(4, lines.size());
            assertEquals("[T][ ] task4", lines.get(3));
        }

        @Test
        void manageTask_invalidInput_doesNotModifyTasklistOrFile() throws IOException {
            TaskManager taskManager = new TaskManager();
            List<String> before = Files.readAllLines(testFile.toPath());

            taskManager.manageTask("invalidCommand xyz");

            assertEquals(3, TaskManager.getTasklist().size());

            List<String> after = Files.readAllLines(testFile.toPath());
            assertEquals(before, after);
        }
    }


}
