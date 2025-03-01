package attendance.util;

import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceDate;
import attendance.domain.AttendanceTime;
import attendance.domain.Attendances;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AttendanceBookFactory {
    private static final LocalTime DEFAULT_TIME = LocalTime.of(17, 59);

    private AttendanceBookFactory() {
    }

    public static AttendanceBook create(final Map<String, List<LocalDateTime>> input, final LocalDate today) {
        AttendanceBook attendanceBook = initAttendanceBook(input, today);
        for (Map.Entry<String, List<LocalDateTime>> entry : input.entrySet()) {
            applyAttendances(entry, attendanceBook);
        }
        return attendanceBook;
    }

    private static void applyAttendances(Entry<String, List<LocalDateTime>> entry, AttendanceBook attendanceBook) {
        for (LocalDateTime dateTime : entry.getValue()) {
            attendanceBook.updateAttendance(entry.getKey(), dateTime);
        }
    }

    private static AttendanceBook initAttendanceBook(final Map<String, List<LocalDateTime>> input,
                                                     final LocalDate today) {
        Map<String, Attendances> crewAttendances = new HashMap<>();
        for (String nickname : input.keySet()) {
            Attendances attendances = new Attendances(initAttendances(today));
            crewAttendances.put(nickname, attendances);
        }
        return new AttendanceBook(crewAttendances);
    }

    private static List<Attendance> initAttendances(final LocalDate today) {
        List<Attendance> attendances = new ArrayList<>();
        for (int day = 1; day < today.getDayOfMonth(); day++) {
            try {
                AttendanceDate attendanceDate = AttendanceDate.from(today.withDayOfMonth(day));
                AttendanceTime attendanceTime = AttendanceTime.from(DEFAULT_TIME);
                Attendance attendance = Attendance.of(attendanceDate, attendanceTime);
                attendances.add(attendance);
            } catch (Exception ignored) {
            }
        }
        return attendances;
    }
}
