import enums.CommandType;
import exceptions.DeadlineException;

import java.util.AbstractMap;
import java.util.ArrayList;

public class DeadlineParser {
    protected static void parseDeadline(AbstractMap.SimpleEntry<CommandType, ArrayList<String>> deadline) throws DeadlineException {
        ArrayList<String> deadlineInformation = deadline.getValue();
        DeadlineChecker.checkDeadlineByKeyword(deadlineInformation.get(0));
        splitDeadlineInformation(deadlineInformation);
        DeadlineChecker.checkDeadlineFormat(deadlineInformation);
        deadline.setValue(deadlineInformation);
    }

    private static void splitDeadlineInformation(ArrayList<String> deadlineInformation) {
        String[] info = deadlineInformation.get(0).split("/by", -1);
        String taskName = info[0].trim();
        String by = info[1].trim();
        deadlineInformation.set(0, taskName);
        deadlineInformation.add(by);
    }

}
