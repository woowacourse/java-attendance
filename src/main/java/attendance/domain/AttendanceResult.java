package attendance.domain;

import static attendance.domain.DayOfWeek.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceResult {

    private final Map<AttendanceType, Long> attendanceResult;

    private AttendanceResult(Map<AttendanceType, Long> attendanceResult) {
        this.attendanceResult = attendanceResult;
    }

    public static AttendanceResult from(Map<AttendanceType, Long> attendanceResult) {
        return new AttendanceResult(attendanceResult);
    }

    public static AttendanceResult calculateAttendanceResult(LocalDate currentDate,
        AttendanceTimes attendanceTimes) {
        calculateAbsenceDate(currentDate, attendanceTimes);
        Map<AttendanceType, Long> attendanceResult = attendanceTimes.getAttendanceTimes().stream()
            .collect(Collectors.groupingBy(
                AttendanceType::decideAttendanceType,
                Collectors.counting()));
        return AttendanceResult.from(attendanceResult);
    }

    public Map<AttendanceType, Long> getAttendanceResult() {
        return Collections.unmodifiableMap(attendanceResult);
    }

    private static void calculateAbsenceDate(LocalDate currentDate, AttendanceTimes attendanceTimes) {
        int day = currentDate.getDayOfMonth();
        for (int i = 1; i < day; i++) {
            addAbsenceDate(currentDate, attendanceTimes, i);
        }
    }

    private static boolean isDayOff(LocalDate currentDate) {
        DayOfWeek dayOfWeek = findDayOfWeek(currentDate);
        if (Holiday.isHoliday(currentDate)) {
            return true;
        }
        if (dayOfWeek == SATURDAY || dayOfWeek == SUNDAY) {
            return true;
        }
        return false;
    }

    private static void addAbsenceDate(LocalDate currentDate, AttendanceTimes attendanceTimes, int i) {
        LocalDate findDate = currentDate.withDayOfMonth(i);
        if (isDayOff(findDate)) {
            return;
        }
        if (!attendanceTimes.hasAttendanceOnDate(findDate)) {
            AttendanceTime attendanceTime = AttendanceTime.from(findDate);
            attendanceTimes.add(attendanceTime);
        }
    }
}
