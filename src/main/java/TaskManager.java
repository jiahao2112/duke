import java.util.ArrayList;

public class TaskManager {
    private final ArrayList<Task> taskList = new ArrayList<>();

    public void manageTask(String userInput) {
        ArrayList<String> commands = InputParser.parseInput(userInput);
        switch (commands.get(0)) {
            case "list":
                displayTasks();
                break;
            case "mark":
                markTask(commands.get(1),true);
                break;
            case "unmark":
                markTask(commands.get(1),false);
                break;
            default:
                addTask(commands);
        }
    }

    public void displayTasks() {
        int taskNumber = 1;
        for (Task task : taskList) {
            Groot.echo(taskNumber + ": " + task.toString());
            taskNumber++;
        }
    }

    public void markTask(String taskNumber, boolean markDone) {
        int taskNum = InputParser.getTaskNumber(taskNumber);
        Task task = taskList.get(taskNum - 1);
        task.setIsDone(markDone);
        if (markDone) {
            Groot.echo("Task marked as done: " + task);
        } else {
            Groot.echo("Task marked as not done yet: " + task);
        }
    }

    public Task createTask(ArrayList<String> taskInfo){
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
                return null;
        }
        return task;
    }

    public void addTask(ArrayList<String> text) {
        Task task = createTask(text);
        taskList.add(task);
        Groot.echo("Task added: " + taskList.get(taskList.size()-1)); //get last added task
        Groot.echo("Now you have " + taskList.size() + " tasks in the list.");
    }
}
