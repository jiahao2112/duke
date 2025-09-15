import exceptions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {
    private static final File folder = new File("data");
    private static final File file = new File(folder, "tasklist.txt");

    public static void createFile() throws FileException {
        if (!folder.exists()){
            if (!folder.mkdirs()){
                throw new FileException.UnableToCreateDirectoryException();
            }
        }
        try{
            if (!file.exists()){
                if (!file.createNewFile()){
                    throw new FileException.UnableToCreateFileException();
                }
            }
        }catch (IOException e){
            throw new FileException.UnableToCreateFileException();
        }

    }
    public static ArrayList<Task> readFile() throws GrootException {
        Scanner scanTasklist;
        try{
            scanTasklist = new Scanner(file);
        }catch(FileNotFoundException e){
            throw new FileException.FileDoesNotExistException();
        }
        ArrayList<Task> tasklist= new ArrayList<>();
        while (scanTasklist.hasNextLine()){
            String line = scanTasklist.nextLine();
            if (line.isBlank()){
                break;
            }
            Task task = InputParser.parseTaskFile(line);
            tasklist.add(task);
        }
        return tasklist;
    }

    public static void writeToFile(Task task) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(task.toString());
        fileWriter.write(System.lineSeparator());
        fileWriter.close();
    }

    public static void saveFile(ArrayList<Task> tasklist) throws FileException {
        if (tasklist.isEmpty()){
            return;
        }
        try {
            for (Task task : tasklist) {
                writeToFile(task);
            }
        }catch (IOException e){
            throw new FileException.UnableToWriteFileException();
        }
    }
}
