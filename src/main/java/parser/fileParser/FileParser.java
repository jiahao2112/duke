package parser.fileParser;

import enums.CommandType;
import exceptions.FileException;

import java.util.AbstractMap;
import java.util.ArrayList;
import tasks.Task;
import manager.TaskManager;

public class FileParser {

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
        Task task = TaskManager.createTask(new AbstractMap.SimpleEntry<>(taskType, taskInfoInput));
        task.setIsDone(taskDone);
        return task;
    }

    private CommandType parseCommandType(String taskLine) throws FileException {
        String taskType = taskLine.substring(1, 2);
        return switch (taskType) {
            case "T" -> CommandType.TODO;
            case "D" -> CommandType.DEADLINE;
            case "E" -> CommandType.EVENT;
            default -> throw new FileException.FileCorruptedException();
        };
    }

    private boolean taskIsDone(String taskLine) {
        String taskIsDone = taskLine.substring(4, 5);
        return taskIsDone.equals("X");
    }
}
