package dto;

import java.time.LocalDate;
import java.time.LocalTime;
import util.InputParser;

public record AddAttendanceRequest(
        String name,
        LocalDate date,
        LocalTime time
) {
    public static AddAttendanceRequest fromDataLine(String dataLine) {
        return new AddAttendanceRequest(
                InputParser.parseNameFromDateLine(dataLine),
                InputParser.parseDateFromDataLine(dataLine),
                InputParser.parseTimeFromDataLIne(dataLine)
        );
    }
}
