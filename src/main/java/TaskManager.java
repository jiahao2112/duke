import java.util.ArrayList;

import exceptions.*;

public class TaskManager {
    private final ArrayList<Task> taskList = new ArrayList<>();

    public void manageTask(String userInput) {
        ArrayList<String> commands;
        try {
            commands = InputParser.parseInput(userInput);
            switch (commands.get(0)) {
                case "list":
                    displayTasks();
                    break;
                case "mark":
                    markTask(commands.get(1), true);
                    break;
                case "unmark":
                    markTask(commands.get(1), false);
                    break;
                case "delete":
                    deleteTask(commands.get(1));
                    break;
                case "todo":
                case "deadline":
                case "event":
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

            if (task.getIsDone() == markDone) {
                throw new MarkUnmarkDeleteException.TaskAlreadyMarkedException(markDone ? "done" : "not done");
            }
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

    public Task createTask(ArrayList<String> taskInfo) {
        Task task;
        switch (taskInfo.get(0)) {
            case "todo":
                task = new ToDo(taskInfo.get(1));
                break;
            case "deadline":
                task = new Deadline(taskInfo.get(1), taskInfo.get(2));
                break;
            case "event":
                task = new Event(taskInfo.get(1), taskInfo.get(2), taskInfo.get(3));
                break;
            default:
                task = null;
        }
        return task;
    }

    public void addTask(ArrayList<String> text) {
        Task task = createTask(text);
        taskList.add(task);
        printMessage("Task added: " + task,
                "Now you have " + taskList.size() + " tasks in the list.");
    }

    public void deleteTask(String taskNumber) throws MarkUnmarkDeleteException {
        try {
            int taskNum = InputParser.getTaskNumber(taskNumber, taskList.size()); //will check if task number valid after changing to int
            Task task = taskList.get(taskNum - 1);
            taskList.remove(taskNum - 1);
            printMessage("Task deleted: " + task,
                    "Now you have " + taskList.size() + " tasks in the list.");
        } catch (MarkUnmarkDeleteException e) {
            printMessage(e.getMessage());
        }
    }

    public void printMessage(String... messages) {
        for (String message : messages) {
            Groot.echo(message);
        }
    }
}
