package domain;

import controller.AttendanceController;
import controller.DateTimeConverter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import view.AttendanceFileReader;

public class AttendanceBookFactory {
    private final AttendanceFileReader attendanceFileReader;

    public AttendanceBookFactory(AttendanceFileReader attendanceFileReader) {
        this.attendanceFileReader = attendanceFileReader;
    }

    public AttendanceBook generate() {
        Map<String, List<String>> crewsInfo = attendanceFileReader.getInfo();

        Map<String, List<LocalDateTime>> parsedCrewsInfo = parseCrewsDateTime(crewsInfo);
        return new AttendanceBook(parsedCrewsInfo, AttendanceController.START_DATE, AttendanceController.END_DATE);
    }

    private static Map<String, List<LocalDateTime>> parseCrewsDateTime(Map<String, List<String>> crewsInfo) {
        return crewsInfo.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        crewEntry -> crewEntry.getValue().stream()
                                .map(DateTimeConverter::convertStringToLocalDateTime)
                                .toList()
                ));
    }
}
