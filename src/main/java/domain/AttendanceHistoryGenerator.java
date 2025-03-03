package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceHistoryGenerator {
    public static AttendanceHistories generate(Map<String, List<LocalDateTime>> rawAttendanceData) {
        Map<Crew, AttendanceDateTimes> attendanceHistories = rawAttendanceData.entrySet().stream()
                .collect(Collectors.toMap(
                        attendanceData -> new Crew(attendanceData.getKey()),
                        attendanceData -> toAttendanceDateTimes(attendanceData.getValue())
                ));
        return new AttendanceHistories(attendanceHistories);
    }

    private static AttendanceDateTimes toAttendanceDateTimes(List<LocalDateTime> dateTimes) {
        List<AttendanceDateTime> attendanceDateTimes = new ArrayList<>(dateTimes.stream()
                .map(AttendanceDateTime::new)
                .toList());
        return new AttendanceDateTimes(attendanceDateTimes);
    }
}
