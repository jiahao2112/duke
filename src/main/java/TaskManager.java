import java.util.ArrayList;

public class TaskManager {
    private final ArrayList<Task> taskList = new ArrayList<>();

    public void manageTask(String userInput) {
        int commandDivider = userInput.indexOf(" ");
        String command;
        if (commandDivider == -1){
            command = userInput;
        }else{
            command = userInput.substring(0, commandDivider);
        }
        String taskName = userInput.substring(commandDivider + 1);
        switch (command) {
            case "list":
                displayTasks();
                break;
            case "mark":
                markTask(taskName,true);
                break;
            case "unmark":
                markTask(taskName,false);
                break;
            default:
                addTask(userInput);
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
        int taskNum = Integer.parseInt(taskNumber);
        Task task = taskList.get(taskNum - 1);
        task.setIsDone(markDone);
        if (markDone) {
            Groot.echo("Task marked as done: " + task);
        } else {
            Groot.echo("Task marked as not done yet: " + task);
        }
    }

    public void addTask(String text) {
        Task task = new Task(text);
        taskList.add(task);
        Groot.echo("Task added: " + taskList.get(taskList.size()-1)); //get last added task
        Groot.echo("Now you have " + taskList.size() + " tasks in the list.");
    }
}
