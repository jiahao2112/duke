package commands;

import enums.CommandType;
import exceptions.GrootException;
import exceptions.ViewException;
import parser.DateTimeParser;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import ui.UserInteraction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;

public class ShowListCommand extends Command {
    CommandType commandType;
    LocalDate date;

    protected ShowListCommand(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> commandLine, ArrayList<Task> tasklist) throws GrootException {
        super(tasklist);
        this.commandType = commandLine.getKey();
        if (commandType == CommandType.VIEW){
            date = DateTimeParser.parseDate(commandLine.getValue().get(0));
        }
    }

    public void displayTasks() {
        int taskNumber = 1;
        for (Task task : tasklist) {
            UserInteraction.printMessage(taskNumber + ": " + task);
            taskNumber++;
        }
    }

    public void viewTasksOnDate() throws ViewException{
            ArrayList<Task> viewList = new ArrayList<>();
            for (Task task : tasklist) {
                if (task instanceof Deadline) { //if task is a deadline task
                    viewDeadline(task, date, viewList);
                } else if (task instanceof Event) { //if task is an event task
                    viewEvent(task,date,viewList);
                }
            }
            if (viewList.isEmpty()){
                throw new ViewException.NoTaskForViewException();
            }
            for (Task task : viewList) {
                UserInteraction.printMessage(task.toString());
            }
    }

    private void viewDeadline(Task task, LocalDate date, ArrayList<Task> viewList){
        LocalDateTime by = ((Deadline)task).getBy();
        if (by.toLocalDate().equals(date)) {
            viewList.add(task);
        }
    }

    private void viewEvent(Task task, LocalDate date, ArrayList<Task> viewList) {
        LocalDateTime from = ((Event) task).getStartDateTime();
        LocalDateTime to = ((Event) task).getEndDateTime();
        if ((from.toLocalDate().isBefore(date) && to.toLocalDate().isAfter(date)) || //given date is between
                (from.toLocalDate().equals(date) || to.toLocalDate().equals(date)) //given date is either from or to date
        ) {
            viewList.add(task);
        }
    }

    @Override
    public void execute() throws GrootException {
        if (tasklist.isEmpty()){
            throw new GrootException.EmptyListException();
        }
        switch (commandType){
            case VIEW -> viewTasksOnDate();
            case LIST -> displayTasks();
        }
    }
}
