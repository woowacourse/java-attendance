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

    public Map<Crew, List<AttendanceDateTime>> parseTexts(final List<String> crewAttendanceTexts) {
        return crewAttendanceTexts.stream()
                .map(attendanceText -> attendanceText.split(CREW_DATETIME_DELIMITER))
                .collect(Collectors.toMap(text -> new Crew(text[0]),
                        text -> new ArrayList<>(List.of(new AttendanceDateTime(parseDateTime(text[1])))),
                        (existingList, newList) -> mergeTwoLists(existingList, newList)));
    }

    private LocalDateTime parseDateTime(final String localDateTimeText) {
        return LocalDateTime.parse(localDateTimeText, DATE_TIME_FORMATTER);
    }

    private List<AttendanceDateTime> mergeTwoLists(final List<AttendanceDateTime> existingList,
                                                   final List<AttendanceDateTime> newList) {
        existingList.addAll(newList);
        return existingList;
    }
}
