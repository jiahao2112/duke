package gui;

import exceptions.GrootException;
import manager.TaskManager;

public class GrootGUI {
    TaskManager taskManager;

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) throws GrootException {
        taskManager = new TaskManager();
        return taskManager.manageTask(input.trim(), true);
    }

    /*
     * Greet user
     */
    public static String greet() {
        return iAmGroot()+"(Hello. What can I do for you?)";
    }

    public static String iAmGroot() {
        return "I am Groot.\n";
    }

    public static String echo(String text) {
        return "(" + text + ")";
    }

    public static String buildReply(String... messages) {
        StringBuilder sb = new StringBuilder();
        sb.append(iAmGroot());
        for (String message : messages) {
            message = echo(message);
            sb.append(message).append("\n");
        }
        return sb.toString();
    }

    public static String exit() {
        return "We are Groot. (Goodbye!)";
    }
}
