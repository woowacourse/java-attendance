package attendance.controller.util;

import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.Attendances;
import static attendance.domain.CampusOperatingRule.CAMPUS_CLOSE_HOUR;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

public class AttendanceBookFactory {

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
            Attendance attendance = initAttendance(today, day);
            attendances.add(attendance);
        }
        return attendances.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static Attendance initAttendance(final LocalDate today, final int day) {
        try {
            LocalDate date = today.withDayOfMonth(day);
            LocalTime absenceTime = CAMPUS_CLOSE_HOUR.getTime().minusMinutes(1);
            return Attendance.from(date, absenceTime);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
