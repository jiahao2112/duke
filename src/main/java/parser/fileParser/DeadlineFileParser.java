package parser.fileParser;

import checker.DeadlineChecker;
import exceptions.DeadlineException;
import exceptions.FileException;

import java.util.ArrayList;

public class DeadlineFileParser {
    protected static void parseDeadlineFile(ArrayList<String> deadlineFile) throws FileException {
        try {
            deadlineFile.set(0, deadlineFile.get(0).trim());
            String taskInfo = deadlineFile.get(0);
            String[] taskInfoArray = taskInfo.split("\\| by:");
            deadlineFile.set(0, taskInfoArray[0].trim());
            deadlineFile.add(1, taskInfoArray[1].trim());
            DeadlineChecker.checkDeadlineFormat(deadlineFile);
        } catch (DeadlineException e) {
            throw new FileException.FileCorruptedException();
        }
    }
}
