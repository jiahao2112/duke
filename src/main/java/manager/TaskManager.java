package manager;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;

import enums.*;
import exceptions.*;
import commands.Command;
import parser.userInputParser.UserInputParser;
import tasks.*;
import ui.UserInteraction;

public class TaskManager {
    private static final ArrayList<Task> tasklist = new ArrayList<>();

    public TaskManager() {
        try {
            FileManager.createFile(); //create tasklist file if not exist
            clearTasklist(); //ensure tasklist is empty before updating with file content
            tasklist.addAll(FileManager.readFile()); //load tasklist content
        }catch (FileException.FileCorruptedException e) {
            UserInteraction.printMessage(e.getMessage());
            UserInteraction.fileCorruptedHandler();
        }catch (FileException e) {
            UserInteraction.printMessage(e.getMessage());
        }
    }

    public static void clearTasklist() {
        tasklist.clear();
    } //clear tasklist for initial file read and if error in parsing

    public static ArrayList<Task> getTasklist() {
        return tasklist;
    }

    public void manageTask(String userInput) {
        try {
            AbstractMap.SimpleEntry<CommandType, ArrayList<String>> commandLine = UserInputParser.parseUserInput(userInput);
            Command cmd = Command.createCommand(commandLine, tasklist);
            if (cmd == null) { //No command given
                return;
            }
            cmd.execute();
            FileManager.saveFile(tasklist);
        } catch (GrootException e) {
            UserInteraction.printMessage(e.getMessage());
        }
    }
}
