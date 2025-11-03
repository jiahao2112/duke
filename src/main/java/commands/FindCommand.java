package commands;

import enums.CommandType;
import exceptions.FindException;
import gui.GrootGUI;
import tasks.Task;

import java.util.AbstractMap;
import java.util.ArrayList;

public class FindCommand extends Command {
    private ArrayList<Task> foundList = new ArrayList<>();

    protected FindCommand(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> commandLine,
                          ArrayList<Task> tasklist) throws FindException {
        super(tasklist);

        assert commandLine != null;
        assert commandLine.getValue()!=null;
        assert !commandLine.getValue().isEmpty();

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

    private String printFoundTasks() {
        ArrayList<String> message = new ArrayList<>();
        message.add("Found " + foundList.size() + " tasks");

        for (Task task : foundList) {
            message.add(task.toString());
        }

        return GrootGUI.buildReply(message.toArray(new String[0]));
    }

    @Override
    public String execute() {
        return printFoundTasks();
    }
}
