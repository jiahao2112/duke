package manager;

import exceptions.FileException;
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
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileManagerTests {
    @TempDir
    File tempDir; // JUnit temporary directory

    File testFolder;
    File testFile;

    @BeforeEach
    void setup() throws IOException {
        // Create temporary folder and file
        testFolder = new File(tempDir, "data");
        assertTrue(testFolder.mkdirs());
        testFile = new File(testFolder, "tasklist.txt");
        assertTrue(testFile.createNewFile());

        FileManager.setFolderAndFile(testFolder, testFile);
    }

    @Nested
    @DisplayName("createFile()")
    class CreateFileTest {
        @Test
        @DisplayName("Success")
        void createFileTest_Success() {
            assertDoesNotThrow(FileManager::createFile);
            assertTrue(testFolder.exists());
            assertTrue(testFile.exists());
        }

        @Test
        void createFileTest_folderExists() {
            // Folder already exists, created in setup
            assertDoesNotThrow(FileManager::createFile);
        }

        @Test
        void createFileTest_fileExists() {
            assertDoesNotThrow(FileManager::createFile);
        }
    }

    @Nested
    @DisplayName("saveFile()")
    class SaveFileTest {
        @Test
        @DisplayName("Success")
        void SaveFileTest_Success() {
            ArrayList<Task> tasks = new ArrayList<>();
            tasks.add(new ToDo("Task1"));

            assertDoesNotThrow(() -> FileManager.saveFile(tasks));

            List<String> lines = assertDoesNotThrow(() -> Files.readAllLines(testFile.toPath()));
            assertEquals(1, lines.size());
            assertEquals("[T][ ] Task1", lines.get(0));
        }

        @Test
        @DisplayName("Throws UnableToWriteFileException")
        void SaveFileTest_UnableToWriteFile() {
            assertTrue(testFile.setReadOnly());
            ArrayList<Task> tasks = new ArrayList<>();
            tasks.add(new ToDo("Task1"));

            FileException fileException = assertThrows(FileException.UnableToWriteFileException.class,
                    () -> FileManager.saveFile(tasks));

            assertEquals("Error while writing file.", fileException.getMessage());

            assertTrue(testFile.setWritable(true));
        }
    }

    @Nested
    @DisplayName("readFile()")
    class ReadFileTest {
        @Test
        @DisplayName("Success")
        void ReadFileTest_Success() {
            assertDoesNotThrow(() -> {
                try (FileWriter writer = new FileWriter(testFile)) {
                    writer.write("[T][X] task1\n");
                    writer.write("[D][ ] task2 | by: 13 Oct 2025 0000\n");
                    writer.write("[E][ ] task3 | from: 13 Oct 2025 0000, to: 14 Oct 2025 0000\n");
                }
            });
            ArrayList<Task> tasks = assertDoesNotThrow(FileManager::readFile);
            assertEquals(3, tasks.size());

            assertInstanceOf(ToDo.class, tasks.get(0));
            assertEquals("task1", tasks.get(0).getDescription());
            assertTrue(tasks.get(0).getIsDone());

            assertInstanceOf(Deadline.class, tasks.get(1));
            assertEquals("task2", tasks.get(1).getDescription());
            assertFalse(tasks.get(1).getIsDone());
            assertEquals(LocalDateTime.parse("2025-10-13T00:00:00"), ((Deadline) tasks.get(1)).getBy());

            assertInstanceOf(Event.class, tasks.get(2));
            assertEquals("task3", tasks.get(2).getDescription());
            assertFalse(tasks.get(2).getIsDone());
            assertEquals(LocalDateTime.parse("2025-10-13T00:00:00"), ((Event) tasks.get(2)).getStartDateTime());
            assertEquals(LocalDateTime.parse("2025-10-14T00:00:00"), ((Event) tasks.get(2)).getEndDateTime());
        }

        @Test
        @DisplayName("MissingTaskDoneStatus Throws FileCorruptedException")
        void ReadFileTest_FileCorrupted_MissingTaskDoneStatus() {
            assertDoesNotThrow(() -> {
                try (FileWriter writer = new FileWriter(testFile)) {
                    writer.write("[T] task1\n");
                }
            });
            FileException fileException = assertThrows(FileException.FileCorruptedException.class,
                    FileManager::readFile);

            assertEquals("Tasklist file is corrupted.", fileException.getMessage());
        }

        @Test
        @DisplayName("MissingTaskType Throws FileCorruptedException")
        void ReadFileTest_FileCorrupted_MissingTaskType() {
            assertDoesNotThrow(() -> {
                try (FileWriter writer = new FileWriter(testFile)) {
                    writer.write("[] task1\n");
                }
            });
            FileException fileException = assertThrows(FileException.FileCorruptedException.class,
                    FileManager::readFile);

            assertEquals("Tasklist file is corrupted.", fileException.getMessage());
        }

        @Test
        @DisplayName("MissingTodoTaskName Throws FileCorruptedException")
        void ReadFileTest_FileCorrupted_MissingTodoTaskName() {
            assertDoesNotThrow(() -> {
                try (FileWriter writer = new FileWriter(testFile)) {
                    writer.write("[T][ ]\n");
                }
            });
            FileException fileException = assertThrows(FileException.FileCorruptedException.class,
                    FileManager::readFile);

            assertEquals("Tasklist file is corrupted.", fileException.getMessage());
        }

        @Test
        @DisplayName("MissingDeadlineInfo Throws FileCorruptedException")
        void ReadFileTest_FileCorrupted_MissingDeadlineInfo() {
            assertDoesNotThrow(() -> {
                try (FileWriter writer = new FileWriter(testFile)) {
                    writer.write("[D][ ] \n");
                }
            });
            FileException fileException = assertThrows(FileException.FileCorruptedException.class,
                    FileManager::readFile);

            assertEquals("Tasklist file is corrupted.", fileException.getMessage());
        }

        @Test
        @DisplayName("MissingEventInfo Throws FileCorruptedException")
        void ReadFileTest_FileCorrupted_MissingEventInfo() {
            assertDoesNotThrow(() -> {
                try (FileWriter writer = new FileWriter(testFile)) {
                    writer.write("[E][ ] \n");
                }
            });
            FileException fileException = assertThrows(FileException.FileCorruptedException.class,
                    FileManager::readFile);

            assertEquals("Tasklist file is corrupted.", fileException.getMessage());
        }
    }
}
