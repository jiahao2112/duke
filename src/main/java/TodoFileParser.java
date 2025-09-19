import exceptions.FileException;
import exceptions.TodoException;

import java.util.ArrayList;

public class TodoFileParser {
    protected static void parseTodoFile(ArrayList<String> todoFile) throws FileException {
        try {
            todoFile.set(0, todoFile.get(0).trim());
            TodoChecker.checkTodoTaskName(todoFile.get(0));
        } catch (TodoException e) {
            throw new FileException.FileCorruptedException();
        }
    }
}
