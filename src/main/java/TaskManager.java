import java.util.AbstractMap;
import java.util.ArrayList;

import enums.*;
import exceptions.*;

public class TaskManager {
    private static ArrayList<Task> taskList = new ArrayList<>();

    public TaskManager(){
        try{
            FileManager.createFile();
            taskList = FileManager.readFile();
        }catch(FileException e){
            printMessage(e.getMessage());
            Groot.exit();
        }catch (GrootException e){
            printMessage(e.getMessage());
        }
    }
    public void manageTask(String userInput) {
        try {
            AbstractMap.SimpleEntry<CommandType, ArrayList<String>> commands = InputParser.parseInput(userInput);
            CommandType command = commands.getKey();
            switch (command) {
                case LIST:
                    displayTasks();
                    break;
                case MARK:
                    markTask(commands.getValue().get(0), true);
                    break;
                case UNMARK:
                    markTask(commands.getValue().get(0), false);
                    break;
                case DELETE:
                    deleteTask(commands.getValue().get(0));
                    break;
                case TODO:
                case DEADLINE:
                case EVENT:
                    addTask(commands);
                    break;
                default:
            }
        } catch (GrootException e) {
            printMessage(e.getMessage());
        }

    }

    public void displayTasks() {
        try {
            if (taskList.isEmpty()) {
                throw new GrootException.EmptyListException();
            }
            int taskNumber = 1;
            for (Task task : taskList) {
                Groot.echo(taskNumber + ": " + task.toString());
                taskNumber++;
            }
        } catch (GrootException.EmptyListException e) {
            printMessage(e.getMessage());
        }

    }

    public void markTask(String taskNumber, boolean markDone) {
        try {
            Task task;
            int taskNum;
            taskNum = InputParser.getTaskNumber(taskNumber, taskList.size());
            task = taskList.get(taskNum - 1);
            InputChecker.checkTaskStatus(task, markDone);
            task.setIsDone(markDone);
            if (markDone) {
                printMessage("Task marked as done: " + task);
            } else {
                printMessage("Task marked as not done yet: " + task);
            }
        } catch (MarkUnmarkDeleteException e) {
            printMessage(e.getMessage());
        }
    }

    public Task createTask(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> taskInfo) {
        try{
            Task task;
            TaskType taskType = InputParser.parseTask(taskInfo.getKey());
            task = switch (taskType) {
                case TODO -> new ToDo(taskInfo.getValue().get(0));
                case DEADLINE -> new Deadline(taskInfo.getValue().get(0), taskInfo.getValue().get(1));
                case EVENT -> new Event(taskInfo.getValue().get(0), taskInfo.getValue().get(1), taskInfo.getValue().get(2));
            };
            return task;
        }catch (GrootException e){
            printMessage(e.getMessage());
            return null;
        }

    }

    public void addTask(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> text) throws  GrootException {
        Task task = createTask(text);
        taskList.add(task);
        FileManager.saveFile(taskList);
        printMessage("Task added: " + task,
                "Now you have " + taskList.size() + " tasks in the list.");
    }

    public void deleteTask(String taskNumber) throws GrootException {
        try {
            int taskNum = InputParser.getTaskNumber(taskNumber, taskList.size()); //will check if task number valid after changing to int
            Task task = taskList.get(taskNum - 1);
            taskList.remove(taskNum - 1);
            FileManager.saveFile(taskList);
            printMessage("Task deleted: " + task,
                    "Now you have " + taskList.size() + " tasks in the list.");
        } catch (GrootException e) {
            printMessage(e.getMessage());
        }
    }

    public void printMessage(String... messages) {
        for (String message : messages) {
            Groot.echo(message);
        }
    }
}
