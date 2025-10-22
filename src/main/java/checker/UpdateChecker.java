package checker;

import exceptions.UpdateException;

import java.util.ArrayList;

public class UpdateChecker {
    /**
     * Checks if task number given by user is empty or not digit
     *
     * @param taskNumber task number given by user
     * @throws UpdateException if task number is invalid
     */
    public static void checkTaskNumberFormat(String taskNumber) throws UpdateException {
        checkTaskNumberEmpty(taskNumber); //check if there is task number i.e. not empty
        checkTaskNumberDigits(taskNumber); //check if task number is a digit
    }

    /*
     * Used by checkTaskNumberFormat
     * Checks if task number is empty
     */
    private static void checkTaskNumberEmpty(String taskNumber)
            throws UpdateException.MissingTaskNumberException {
        if (taskNumber.isEmpty()) {
            throw new UpdateException.MissingTaskNumberException();
        }
    }

    /*
     * Used by checkTaskNumberFormat
     * Checks if task number is a digit
     */
    private static void checkTaskNumberDigits(String taskNumber)
            throws UpdateException.InvalidTaskNumberException {
        if (!taskNumber.matches("\\d+")) {
            throw new UpdateException.InvalidTaskNumberException();
        }
    }

    /**
     * Checks if task number is within size of task list
     *
     * @param taskNumber   task number given by user
     * @param tasklistSize size of task list
     * @throws UpdateException.TaskNotFoundException if task number is out of task list range, i.e. task number is more than
     *                                               task list size or less than 1
     */
    public static void checkTaskNumberValid(int taskNumber, int tasklistSize)
            throws UpdateException.TaskNotFoundException {
        if (taskNumber > tasklistSize || taskNumber <= 0) {
            throw new UpdateException.TaskNotFoundException();
        }
    }

    public static void checkTodoUpdateValid(ArrayList<String> update) throws UpdateException {
        try {
            if (!update.get(0).equals("taskName")) {
                throw new UpdateException.InvalidUpdateTodoFieldException();
            }
            if (update.get(1).isEmpty()) {
                throw new UpdateException.InvalidUpdateTodoInfoException();
            }
        } catch (IndexOutOfBoundsException e) {
            throw new UpdateException.InvalidUpdateFormatException();
        }
    }

    public static void checkDeadlineUpdateValid(ArrayList<String> update) throws UpdateException {
        try {
            for (int i = 0; i < update.size(); i += 2) {
                String field = update.get(i);
                if (!field.equals("taskName") && !field.equals("by")) {
                    throw new UpdateException.InvalidUpdateDeadlineFieldException();
                }
                if (update.get(i + 1).isEmpty()) {
                    throw new UpdateException.InvalidUpdateDeadlineInfoException();
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new UpdateException.InvalidUpdateFormatException();
        }
    }

    public static void checkEventUpdateValid(ArrayList<String> update) throws UpdateException {
        try {
            for (int i = 0; i < update.size(); i += 2) {
                String field = update.get(i);
                if (!field.equals("taskName") && !field.equals("start") && !field.equals("end")) {
                    throw new UpdateException.InvalidUpdateEventFieldException();
                }
                if (update.get(i + 1).isEmpty()) {
                    throw new UpdateException.InvalidUpdateEventInfoException();
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new UpdateException.InvalidUpdateFormatException();
        }
    }
}
