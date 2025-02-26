package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceBookFactory {

    public static AttendanceBook create(Map<String, List<LocalDateTime>> input, LocalDate today) {
        Map<String, Attendances> crewAttendances = new HashMap<>();
        for (String nickname : input.keySet()) {
            Attendances attendances = new Attendances(initAttendances(input.get(nickname), today));
            crewAttendances.put(nickname, attendances);
        }
        return new AttendanceBook(crewAttendances);
    }

    // TODO : indent 및 메서드 라인 줄이기
    public static List<Attendance> initAttendances(List<LocalDateTime> inputDateTimes, LocalDate today) {
        List<Attendance> attendances = new ArrayList<>();
        int count = 0;
        for (int day = 1; day < today.getDayOfMonth(); day++) {
            LocalDateTime dateTime = inputDateTimes.get(count);
            if (dateTime.getDayOfMonth() == day) {
                count++;
            } else {
                dateTime = LocalDateTime.of(today.withDayOfMonth(day), LocalTime.of(17, 59));
            }
            try {
                Attendance attendance = Attendance.from(dateTime);
                attendances.add(attendance);
            } catch (IllegalArgumentException ignored) {
            }
        }
        return attendances;
    }
}
