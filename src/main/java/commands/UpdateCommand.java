package commands;

import checker.UpdateChecker;
import enums.CommandType;
import exceptions.DateTimeException;
import exceptions.TaskNumberException;
import exceptions.UpdateException;
import parser.DateTimeParser;
import parser.userInputParser.TaskNumberParser;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;
import ui.UserInteraction;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;

public class UpdateCommand extends Command {
    private final Task task;
    private String taskName;
    private LocalDateTime byDate;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    public UpdateCommand(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> update,
                         ArrayList<Task> tasklist) throws UpdateException {
        super(tasklist);
        int taskNumber;

        try {
            taskNumber = TaskNumberParser.getTaskNumber(update.getValue().get(0), tasklist.size());
        } catch (TaskNumberException e) {
            throw new UpdateException(e.getMessage());
        }

        task = tasklist.get(taskNumber - 1);
        update.getValue().remove(0); //remove task number from update

        setUpdateFields(update.getValue());
    }

    private void splitUpdateFields(ArrayList<String> update, ArrayList<String> updateFields) throws UpdateException {
        try {
            for (String field : updateFields) {
                String[] fields = field.split(":", -1);

                update.add(fields[0].trim());
                update.add(fields[1].trim());
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new UpdateException.InvalidUpdateFormatException();
        }
    }

    private void setFields(ArrayList<String> update) throws UpdateException {
        try {
            for (int i = 0; i < update.size(); i += 2) {
                switch (update.get(i)) {
                case "taskName" -> taskName = update.get(i + 1);
                case "by" -> byDate = DateTimeParser.parseDateTime(update.get(i + 1));
                case "from" -> fromDate = DateTimeParser.parseDateTime(update.get(i + 1));
                case "to" -> toDate = DateTimeParser.parseDateTime(update.get(i + 1));
                }
            }
        } catch (DateTimeException e) {
            throw new UpdateException.InvalidUpdateDeadlineDateException();
        }
    }

    private void setUpdateFields(ArrayList<String> updateFields) throws UpdateException {
        ArrayList<String> update = new ArrayList<>();
        boolean isTodo = task instanceof ToDo;
        boolean isDeadline = task instanceof Deadline;
        boolean isEvent = task instanceof Event;

        splitUpdateFields(update, updateFields);

        if (isTodo) {
            UpdateChecker.checkTodoUpdateValid(update);
        } else if (isDeadline) {
            UpdateChecker.checkDeadlineUpdateValid(update);
        } else if (isEvent) {
            UpdateChecker.checkEventUpdateValid(update);
        }

        setFields(update);
    }

    private void updateTask() {
        boolean isTodo = task instanceof ToDo;
        boolean isDeadline = task instanceof Deadline;
        boolean isEvent = task instanceof Event;

        if (isTodo) {
            ToDo todoTask = ((ToDo) task);

            if (taskName != null) {
                todoTask.setDescription(taskName);
            }
        } else if (isDeadline) { // ensure type casting works
            Deadline deadlineTask = ((Deadline) task);

            if (taskName != null) {
                deadlineTask.setDescription(taskName);
            }
            if (byDate != null) {
                deadlineTask.setBy(byDate);
            }
        } else if (isEvent) {
            Event eventTask = ((Event) task);

            if (taskName != null) {
                eventTask.setDescription(taskName);
            }
            if (fromDate != null) {
                eventTask.setStartDateTime(fromDate);
            }
            if (toDate != null) {
                eventTask.setEndDateTime(toDate);
            }
        }

        UserInteraction.printMessage("task updated: " + task);
    }

    @Override
    public void execute() {
        updateTask();
    }
}
