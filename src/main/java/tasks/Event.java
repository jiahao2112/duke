package tasks;

import java.time.LocalDateTime;
import parser.DateTimeParser;

public class Event extends Task {
    protected LocalDateTime startDateTime;
    protected LocalDateTime endDateTime;

    public Event(String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(description);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return  endDateTime;
    }
    @Override
    public String toString() {
        return "[E]" + super.toString() + " | from: " + DateTimeParser.formatDateTime(startDateTime) + ", to: " + DateTimeParser.formatDateTime(endDateTime);
    }

}
