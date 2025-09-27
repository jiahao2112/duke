package parser;

import exceptions.DateTimeException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class DateTimeParser {
    private static final String[] acceptedDateTimeFormats = {
            // '-' separator
            // single digit days and months
            "d-M-yy HH:mm",
            "d-M-yy HHmm",
            "d-M-yyyy HH:mm",
            "d-M-yyyy HHmm",
            // single digit days
            "d-MM-yy HH:mm",
            "d-MM-yy HHmm",
            "d-MM-yyyy HH:mm",
            "d-MM-yyyy HHmm",
            // single digit months
            "dd-M-yy HH:mm",
            "dd-M-yy HHmm",
            "dd-M-yyyy HH:mm",
            "dd-M-yyyy HHmm",
            //standard
            "dd-MM-yy HH:mm",
            "dd-MM-yy HHmm",
            "dd-MM-yyyy HH:mm",
            "dd-MM-yyyy HHmm",

            // '/' separator
            // single digit days and months
            "d/M/yy HH:mm",
            "d/M/yy HHmm",
            "d/M/yyyy HH:mm",
            "d/M/yyyy HHmm",
            // single digit days
            "d/MM/yy HH:mm",
            "d/MM/yy HHmm",
            "d/MM/yyyy HH:mm",
            "d/MM/yyyy HHmm",
            // single digit months
            "dd/M/yy HH:mm",
            "dd/M/yy HHmm",
            "dd/M/yyyy HH:mm",
            "dd/M/yyyy HHmm",
            //standard
            "dd/MM/yy HH:mm",
            "dd/MM/yy HHmm",
            "dd/MM/yyyy HH:mm",
            "dd/MM/yyyy HHmm",
            // month in 3 letters
            "dd MMM yyyy HHmm", //used in file
    };
    private static final String[] acceptedDateFormats = {
            // '-' separator
            "d-M-yy",
            "d-M-yyyy",
            "d-MM-yy",
            "d-MM-yyyy",
            "dd-M-yy",
            "dd-M-yyyy",
            "dd-MM-yy",
            "dd-MM-yyyy",

            // '/' separator
            "d/M/yy",
            "d/M/yyyy",
            "d/MM/yy",
            "d/MM/yyyy",
            "dd/M/yy",
            "dd/M/yyyy",
            "dd/MM/yy",
            "dd/MM/yyyy",
    };

    public static LocalDateTime parseDateTime(String date) throws DateTimeException {
        for (String format : acceptedDateTimeFormats) {
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                return  LocalDateTime.parse(date, formatter);
            }catch(DateTimeParseException ignored){
            }
        }
        throw new DateTimeException();
    }

    public static LocalDate parseDate(String date) throws DateTimeException {
        for (String format : acceptedDateFormats) {
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                return LocalDate.parse(date, formatter);
            }catch(DateTimeParseException ignored){
            }
        }
        throw new DateTimeException();
    }

    public static String formatDateTime(LocalDateTime date){
        return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy HHmm"));
    }
}
