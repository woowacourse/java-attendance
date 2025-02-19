package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record AttendanceTimeline(
        List<AttendanceLog> attendanceLogs
) {
    public static AttendanceTimeline generateAttendanceTimelineUntilDate(Set<Attendance> attendances, LocalDate now) {
        Map<LocalDate, LocalTime> map = toMap(attendances);
        List<AttendanceLog> logs = new ArrayList<>();
        for (int date = 1; date <= now.getDayOfMonth(); date++) {
            LocalDate currentDate = LocalDate.of(now.getYear(), now.getMonth(), date);
            if (isCloseDay(currentDate)) {
                continue;
            }
            if (map.containsKey(currentDate)) {
                LocalTime attendanceTime = map.get(currentDate);
                logs.add(new AttendanceLog(
                        currentDate,
                        attendanceTime,
                        AttendanceType.judge(
                                AttendanceStartTime.findDayOfWeek(currentDate.getDayOfWeek()), attendanceTime)
                ));
                continue;
            }
            logs.add(new AttendanceLog(
                    currentDate,
                    null,
                    AttendanceType.ABSENCE
            ));
        }
        return new AttendanceTimeline(logs);
    }

    private static Map<LocalDate, LocalTime> toMap(Set<Attendance> attendances) {
        return attendances.stream()
                .collect(Collectors.toMap(
                        attendance -> attendance.getDateTime().toLocalDate(),
                        attendance -> attendance.getDateTime().toLocalTime()
                ));
    }

    private static boolean isCloseDay(LocalDate date) {
        boolean isWeekend = date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
        return isWeekend || Holiday.isHoliday(LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth()));
    }

    public int countByAttendanceType(AttendanceType attendanceType) {
        return Math.toIntExact(attendanceLogs.stream()
                .filter(attendanceLog -> attendanceLog.attendanceType == attendanceType)
                .count());
    }

    public record AttendanceLog(
            LocalDate date,
            LocalTime time,
            AttendanceType attendanceType
    ) {
    }
}
