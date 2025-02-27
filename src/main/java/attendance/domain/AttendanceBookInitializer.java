package attendance.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceBookInitializer {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final String CREW_DATETIME_DELIMITER = ",";

    public Map<Crew, List<AttendanceDateTime>> parseTexts(List<String> crewAttendanceTexts) {
        return crewAttendanceTexts.stream()
                .map(attendanceText -> attendanceText.split(CREW_DATETIME_DELIMITER))
                .collect(Collectors.toMap(text -> new Crew(text[0]),
                        text -> {
                            LocalDateTime localDateTime = parseDateTime(text[1]);
                            List<AttendanceDateTime> attendanceDateTime = new ArrayList<>();
                            attendanceDateTime.add(new AttendanceDateTime(localDateTime));
                            return attendanceDateTime;},
                        (list1, list2) -> {
                            list1.addAll(list2);
                            return list1;
                        }));
    }

    private LocalDateTime parseDateTime(String localDateTimeText) {
        return LocalDateTime.parse(localDateTimeText, DATE_TIME_FORMATTER);
    }
}
