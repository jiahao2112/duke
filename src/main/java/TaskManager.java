import java.util.AbstractMap;
import java.util.ArrayList;

import enums.*;
import exceptions.*;

public class TaskManager {
    private static ArrayList<Task> taskList = new ArrayList<>();

    public TaskManager() {
        try {
            FileManager.createFile();
            taskList = FileManager.readFile();
        } catch (FileException e) {
            UserInteraction.printMessage(e.getMessage());
        }
    }

    public void manageTask(String userInput) {
        try {
            AbstractMap.SimpleEntry<CommandType, ArrayList<String>> commands = UserInputParser.parseUserInput(userInput);
            CommandType command = commands.getKey();
            switch (command) {
                case BYE:
                    UserInteraction.exit();
                    break;
                case NONE:
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
            FileManager.saveFile(taskList);
        } catch (GrootException e) {
            UserInteraction.printMessage(e.getMessage());
        }

    }

    public void displayTasks() {
        try {
            if (taskList.isEmpty()) {
                throw new GrootException.EmptyListException();
            }
            int taskNumber = 1;
            for (Task task : taskList) {
                UserInteraction.printMessage(taskNumber + ": " + task);
                taskNumber++;
            }
        } catch (GrootException.EmptyListException e) {
            UserInteraction.printMessage(e.getMessage());
        }

    }

    public void markTask(String taskNumber, boolean markDone) {
        try {
            Task task;
            int taskNum;
            taskNum = MarkUnmarkDeleteParser.getTaskNumber(taskNumber, taskList.size());
            task = taskList.get(taskNum - 1);
            MarkUnmarkDeleteChecker.checkTaskStatus(task.getIsDone(), markDone);
            task.setIsDone(markDone);
            if (markDone) {
                UserInteraction.printMessage("Task marked as done: " + task);
            } else {
                UserInteraction.printMessage("Task marked as not done yet: " + task);
            }
        } catch (MarkUnmarkDeleteException e) {
            UserInteraction.printMessage(e.getMessage());
        }
    }

    public static Task createTask(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> taskInfo) {
        Task task;
        task = switch (taskInfo.getKey()) {
            case TODO -> new ToDo(taskInfo.getValue().get(0));
            case DEADLINE -> new Deadline(taskInfo.getValue().get(0), taskInfo.getValue().get(1));
            case EVENT -> new Event(taskInfo.getValue().get(0), taskInfo.getValue().get(1), taskInfo.getValue().get(2));
            default -> null;
        };
        return task;

    }

    public void addTask(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> text) throws GrootException {
        Task task = createTask(text);
        taskList.add(task);
        UserInteraction.printMessage("Task added: " + task,
                "Now you have " + taskList.size() + " tasks in the list.");
    }

    public void deleteTask(String taskNumber) throws GrootException {
        try {
            int taskNum = MarkUnmarkDeleteParser.getTaskNumber(taskNumber, taskList.size()); //will check if task number valid after changing to int
            Task task = taskList.get(taskNum - 1);
            taskList.remove(taskNum - 1);
            UserInteraction.printMessage("Task deleted: " + task,
                    "Now you have " + taskList.size() + " tasks in the list.");
        } catch (GrootException e) {
            UserInteraction.printMessage(e.getMessage());
        }
    }
}
