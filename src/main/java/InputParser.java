import java.util.ArrayList;
public class InputParser {
    public static ArrayList<String> parseInput(String command) {
        String [] input = command.split(" ",2);
        ArrayList<String> userInput =  new ArrayList<>();
        userInput.add(input[0]); //add command to userInput
        switch (input[0]) {
            case "list":
                break;
            case "mark":
            case "unmark":
            case "todo":
                // add task name to userInput {"taskName"}
                userInput.add(input[1]);
                break;
            case "deadline":
                //add additional parsing to userInput {"taskName", "by"}
                userInput.addAll(parseDeadline(input[1]));
                break;
            case "event":
                //add additional parsing to userInput {"taskName", "from", "by"}
                userInput.addAll(parseEvent(input[1])); //add additional parsing to userInput
                break;
            default:
        }
        return userInput;
    }

    public static int getTaskNumber(String taskNumber) {
        return Integer.parseInt(taskNumber);
    }

    public static ArrayList<String> parseDeadline(String deadline) {
        ArrayList<String> deadlineInput =  new ArrayList<>();
        String[] input = deadline.split("/by");
        for (String parts : input) {
            deadlineInput.add(parts.trim());
        }
        return deadlineInput;
    }
    public static ArrayList<String> parseEvent(String event) {
        ArrayList<String> eventInput =  new ArrayList<>();
        String [] input =  event.split("/from|/to");
        for (String s : input) {
            eventInput.add(s.trim());
        }
        return eventInput;
    }
}
