package attendance.model;

import attendance.util.DateUtils;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record AttendanceTimeline(
        List<AttendanceLog> attendanceLogs
) {
    public static AttendanceTimeline generateAttendanceTimelineUntilDate(List<Attendance> attendances, LocalDate now) {
        Map<LocalDate, LocalTime> map = groupByAttendanceDate(attendances);
        List<AttendanceLog> logs = collectAttendanceLogs(now, map);
        return new AttendanceTimeline(logs);
    }

    private static Map<LocalDate, LocalTime> groupByAttendanceDate(List<Attendance> attendances) {
        return attendances.stream()
                .collect(Collectors.toMap(
                        attendance -> attendance.getAttendanceDateTime().toLocalDate(),
                        attendance -> attendance.getAttendanceDateTime().toLocalTime()
                ));
    }

    private static List<AttendanceLog> collectAttendanceLogs(LocalDate now, Map<LocalDate, LocalTime> map) {
        List<AttendanceLog> logs = new ArrayList<>();
        for (int date = 1; date < now.getDayOfMonth(); date++) {
            LocalDate currentDate = LocalDate.of(now.getYear(), now.getMonth(), date);
            if (isCloseDay(currentDate)) {
                continue;
            }
            addAttendanceLog(map, currentDate, logs);
        }
        return logs;
    }

    private static boolean isCloseDay(LocalDate date) {
        return DateUtils.isWeekend(date.getDayOfWeek()) ||
                Holiday.isHoliday(LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth()));
    }

    private static void addAttendanceLog(Map<LocalDate, LocalTime> map,
                                         LocalDate currentDate,
                                         List<AttendanceLog> logs) {
        if (map.containsKey(currentDate)) {
            LocalTime attendanceTime = map.get(currentDate);
            AttendanceType type = judgeAttendanceType(currentDate, attendanceTime);
            logs.add(new AttendanceLog(currentDate, attendanceTime, type));
            return;
        }
        logs.add(new AttendanceLog(currentDate, null, AttendanceType.ABSENCE));
    }

    private static AttendanceType judgeAttendanceType(LocalDate currentDate, LocalTime attendanceTime) {
        LocalTime startTime = AttendanceStartTime.findDayOfWeek(currentDate.getDayOfWeek());
        return AttendanceType.judge(startTime, attendanceTime);
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
