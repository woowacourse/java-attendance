package attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AttendanceParser {

    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private AttendanceParser() {
    }

    public static CrewData parseLine(String line) {
        String[] split = line.split(",");
        return CrewData.of(split[0], LocalDateTime.parse(split[1], DATETIME_FORMATTER));
    }

    public record CrewData(
        String name,
        LocalDate date,
        LocalTime time
    ) {

        private static CrewData of(String name, LocalDateTime dateTime) {
            return new CrewData(name, dateTime.toLocalDate(), dateTime.toLocalTime());
        }
    }
}
