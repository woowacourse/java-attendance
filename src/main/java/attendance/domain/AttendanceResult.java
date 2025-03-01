package attendance.domain;

import static attendance.domain.DayOfWeek.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AttendanceResult {

    public static Map<AttendanceType, Integer> calculateAttendanceResult(LocalDate currentDate,
        AttendanceTimes attendanceTimes) {
        Map<AttendanceType, Integer> attendanceResult = new HashMap<>();
        calculateAbsenceDate(currentDate, attendanceTimes);

        for (AttendanceTime attendanceTime : attendanceTimes.getAttendanceTimes()) {
            AttendanceType attendanceType = AttendanceType.decideAttendanceType(attendanceTime);
            attendanceResult.put(attendanceType,
                attendanceResult.getOrDefault(attendanceType, 0) + 1);
        }
        return attendanceResult;
    }

    private static void calculateAbsenceDate(LocalDate currentDate, AttendanceTimes attendanceTimes) {
        int day = currentDate.getDayOfMonth();
        for (int i = 1; i < day; i++) {
            LocalDate findDate = currentDate.withDayOfMonth(i);
            if (isDayOff(findDate)) {
                continue;
            }
            if (!attendanceTimes.hasAttendanceOnDate(findDate)) {
                AttendanceTime attendanceTime = AttendanceTime.from(findDate);
                attendanceTimes.add(attendanceTime);
            }
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
}
