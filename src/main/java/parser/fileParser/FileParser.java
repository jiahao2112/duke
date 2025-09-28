package parser.fileParser;

import commands.AddCommand;
import enums.CommandType;
import exceptions.FileException;
import tasks.Task;

import java.util.AbstractMap;
import java.util.ArrayList;

/**
 * To delegate the different parsing of task information and create the task based on information
 */
public class FileParser {
    /**
     * Parse task information from tasklist file
     *
     * @param taskFile information in file
     * @return task based on information
     * @throws FileException if there are any errors during function
     */
    public Task parseTaskFile(String taskFile) throws FileException {
        taskFile = taskFile.trim();
        CommandType taskType = parseCommandType(taskFile); //[T/D/E] task definition
        boolean taskDone = taskIsDone(taskFile); // [X/_]
        ArrayList<String> taskInfoInput = new ArrayList<>();
        taskInfoInput.add(taskFile.substring(6));
        switch (taskType) {
            case TODO:
                TodoFileParser.parseTodoFile(taskInfoInput);
                break;
            case DEADLINE:
                DeadlineFileParser.parseDeadlineFile(taskInfoInput);
                break;
            case EVENT:
                EventFileParser.parseEventFile(taskInfoInput);
                break;
            default:
                throw new FileException.FileCorruptedException();
        }
        Task task = AddCommand.createTask(new AbstractMap.SimpleEntry<>(taskType, taskInfoInput));
        if (task == null) {
            return null;
        }
        task.setIsDone(taskDone);
        return task;
    }

    /*
     * Get type of task from task information and convert to CommandType enum
     */
    private CommandType parseCommandType(String taskLine) throws FileException {
        String taskType = taskLine.substring(1, 2);
        return switch (taskType) {
            case "T" -> CommandType.TODO;
            case "D" -> CommandType.DEADLINE;
            case "E" -> CommandType.EVENT;
            default -> throw new FileException.FileCorruptedException();
        };
    }

    /*
     * Get task is marked as done or undone from task information
     */
    private boolean taskIsDone(String taskLine) {
        String taskIsDone = taskLine.substring(4, 5);
        return taskIsDone.equals("X");
    }
}
