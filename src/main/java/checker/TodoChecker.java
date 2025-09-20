package checker;

import exceptions.TodoException;

public class TodoChecker {

    public static void checkTodoTaskName(String taskName) throws TodoException.MissingTodoTaskNameException {
        if (taskName.isEmpty()) {
            throw new TodoException.MissingTodoTaskNameException();
        }
    }
}
