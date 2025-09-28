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

/**
 * Used to display the tasks in task list
 */
public class ShowListCommand extends Command {
    /**
     * commandType: list or view
     * date: date for view command
     */
    private final CommandType commandType;
    private LocalDate date;

    /**
     * Populate required parameters
     *
     * @param commandLine parsed user's input
     * @param tasklist    tasklist from task manager
     * @throws GrootException if there are any errors in populating
     */
    protected ShowListCommand(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> commandLine, ArrayList<Task> tasklist) throws GrootException {
        super(tasklist);
        this.commandType = commandLine.getKey();
        if (commandType == CommandType.VIEW) {
            date = DateTimeParser.parseDate(commandLine.getValue().get(0));
        }
    }

    /**
     * Display entire tasklist
     */
    public void displayTasks() {
        int taskNumber = 1;
        for (Task task : tasklist) {
            UserInteraction.printMessage(taskNumber + ": " + task);
            taskNumber++;
        }
    }

    /**
     * Show tasks with dates, i.e. deadline or event tasks
     * Only shows deadline tasks that have the date as deadline and event tasks where date is between from and to of event
     *
     * @throws ViewException.NoTaskForViewException if there are no errors for viewing
     */
    public void viewTasksOnDate() throws ViewException.NoTaskForViewException {
        ArrayList<Task> viewList = new ArrayList<>();
        for (Task task : tasklist) {
            if (task instanceof Deadline) { //if task is a deadline task
                viewDeadline(task, date, viewList);
            } else if (task instanceof Event) { //if task is an event task
                viewEvent(task, date, viewList);
            }
        }
        if (viewList.isEmpty()) {
            throw new ViewException.NoTaskForViewException();
        }
        for (Task task : viewList) {
            UserInteraction.printMessage(task.toString());
        }
    }

    /*
     * Used by viewTasksOnDate function.
     * Add deadline task if it has the date given as deadline
     */
    private void viewDeadline(Task task, LocalDate date, ArrayList<Task> viewList) {
        LocalDateTime by = ((Deadline) task).getBy();
        if (by.toLocalDate().equals(date)) {
            viewList.add(task);
        }
    }

    /*
     * Used by viewTasksOnDate function
     * Add event task if the date given is between the from and to of event
     */
    private void viewEvent(Task task, LocalDate date, ArrayList<Task> viewList) {
        LocalDateTime from = ((Event) task).getStartDateTime();
        LocalDateTime to = ((Event) task).getEndDateTime();
        if ((from.toLocalDate().isBefore(date) && to.toLocalDate().isAfter(date)) || //given date is between
                (from.toLocalDate().equals(date) || to.toLocalDate().equals(date)) //given date is either from or to date
        ) {
            viewList.add(task);
        }
    }

    /**
     * Execution of command
     * Display entire list or display tasks that fit the date given
     *
     * @throws GrootException if tasklist is empty, i.e. no task to be checked
     */
    @Override
    public void execute() throws GrootException {
        if (tasklist.isEmpty()) {
            throw new GrootException.EmptyListException();
        }
        switch (commandType) {
            case VIEW -> viewTasksOnDate();
            case LIST -> displayTasks();
        }
    }
}
