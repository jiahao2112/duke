package manager;

import exceptions.*;
import parser.fileParser.FileParser;
import tasks.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {
    private static final File folder = new File("data");
    private static final File file = new File(folder, "tasklist.txt");
    private static final FileParser fileParser = new FileParser();

    protected static void createFile() throws FileException {
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                throw new FileException.UnableToCreateDirectoryException();
            }
        }
        try {
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new FileException.UnableToCreateFileException();
                }
            }
        } catch (IOException e) {
            throw new FileException.UnableToCreateFileException();
        }

    }

    protected static ArrayList<Task> readFile() throws FileException {
        try {
            Scanner scanTasklist = new Scanner(file);

            ArrayList<Task> tasklist = new ArrayList<>();
            while (scanTasklist.hasNextLine()) {
                String line = scanTasklist.nextLine();
                if (line.isBlank()) {
                    break;
                }
                Task task = fileParser.parseTaskFile(line);
                tasklist.add(task);
            }
            return tasklist;
        } catch (FileNotFoundException e) {
            throw new FileException.FileDoesNotExistException();
        }
    }


    public static void saveFile(ArrayList<Task> tasklist) throws FileException {
        if (tasklist.isEmpty()) {
            return;
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            for (Task task : tasklist) {
                fileWriter.write(task.toString());
                fileWriter.write(System.lineSeparator());
            }
            fileWriter.close();

        } catch (IOException e) {
            throw new FileException.UnableToWriteFileException();
        }
    }
}
