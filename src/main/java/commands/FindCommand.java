package commands;

import enums.CommandType;
import exceptions.FindException;
import tasks.Task;
import ui.UserInteraction;

import java.util.AbstractMap;
import java.util.ArrayList;

public class FindCommand extends Command {
    private ArrayList<Task> foundList = new ArrayList<>();

    protected FindCommand(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> commandLine,
                          ArrayList<Task> tasklist) throws FindException {
        super(tasklist);
        String keyword = commandLine.getValue().get(0);

        foundList = findTasks(keyword);
        if (foundList.isEmpty()) {
            throw new FindException.TaskNotFoundException();
        }
    }

    private ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> list = new ArrayList<>();

        for (Task task : tasklist) {
            String taskDescription = task.getDescription();

            boolean containsKeyword = taskDescription.contains(keyword);

            if (containsKeyword) {
                list.add(task);
            }
        }
        return list;
    }

    private void printFoundTasks() {
        UserInteraction.printMessage("Found " + foundList.size() + " tasks");

        for (Task task : foundList) {
            UserInteraction.printMessage(task.toString());
        }
    }

    @Override
    public void execute() {
        printFoundTasks();
    }
}
