package commands;

import checker.UpdateChecker;
import enums.CommandType;
import exceptions.DateTimeException;
import exceptions.DeadlineException;
import exceptions.UpdateException;
import parser.DateTimeParser;
import parser.userInputParser.UpdateParser;
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
        int taskNumber = UpdateParser.getTaskNumber(update.getValue().get(0), tasklist.size());
        task = tasklist.get(taskNumber - 1);
        update.getValue().remove(0);
        setUpdateFields(update.getValue());
    }

    private void setUpdateFields(ArrayList<String> updateFields) throws UpdateException {

        ArrayList<String> update = new ArrayList<>();
        try{
            for (String field : updateFields) {
                String[] fields = field.split(":", -1);
                update.add(fields[0].trim());
                update.add(fields[1].trim());
            }
        }catch(ArrayIndexOutOfBoundsException e){
            throw new UpdateException.InvalidUpdateFormatException();
        }
        if (task instanceof ToDo) {
            UpdateChecker.checkTodoUpdateValid(update);
            taskName = update.get(1);
        } else if (task instanceof Deadline) {
            UpdateChecker.checkDeadlineUpdateValid(update);
            for (int i = 0; i < update.size(); i += 2) {
                if (update.get(i).equals("taskName")) {
                    taskName = update.get(i + 1);
                } else if (update.get(i).equals("by")) {
                    try {
                        byDate = DateTimeParser.parseDateTime(update.get(i + 1));
                    } catch (DateTimeException e) {
                        throw new UpdateException.InvalidUpdateDeadlineDateException();
                    }
                }
            }
        } else if (task instanceof Event) {
            UpdateChecker.checkEventUpdateValid(update);
            for (int i = 0; i < update.size(); i += 2) {
                switch (update.get(i)) {
                    case "taskName" -> taskName = update.get(i + 1);
                    case "start" -> {
                        try {
                            fromDate = DateTimeParser.parseDateTime(update.get(i + 1));
                        } catch (DateTimeException e) {
                            throw new UpdateException.InvalidUpdateEventDateException();
                        }
                    }
                    case "end" -> {
                        try {
                            toDate = DateTimeParser.parseDateTime(update.get(i + 1));
                        } catch (DateTimeException e) {
                            throw new UpdateException.InvalidUpdateEventDateException();
                        }
                    }
                }
            }
        }
    }

    private void updateTask(){
        if (task instanceof ToDo) {
            task.setDescription(taskName);
        }
        else if (task instanceof Deadline) {
            if (taskName != null) {
                task.setDescription(taskName);
            }
            if (byDate != null){
                ((Deadline)task).setBy(byDate);
            }
        }else if (task instanceof Event) {
            if (taskName != null) {
                task.setDescription(taskName);
            }
            if (fromDate != null){
                ((Event)task).setStartDateTime(fromDate);
            }
            if (toDate != null){
                ((Event)task).setEndDateTime(toDate);
            }
        }

        UserInteraction.printMessage("task updated: " + task);
    }

    @Override
    public void execute() {
        updateTask();
    }
}
