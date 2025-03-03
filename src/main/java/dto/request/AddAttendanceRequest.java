package dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import util.parser.InputParser;

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
