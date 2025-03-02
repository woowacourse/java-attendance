package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public enum AttendanceStatus {
    ATTENDANCE("출석", 0),
    LATE("지각", 5),
    ABSENT("결석", 30);

    private final String attendanceStatus;
    private final int thresholdMinutes;

    AttendanceStatus(String attendanceStatus, int thresholdMinutes) {
        this.attendanceStatus = attendanceStatus;
        this.thresholdMinutes = thresholdMinutes;
    }

    public static AttendanceStatus calculateAttendanceStatus(LocalDate todayDate, LocalTime attendanceTime) {
        if (attendanceTime == null) {
            return AttendanceStatus.ABSENT;
        }
        LocalTime attendanceStartTime = WeeklyAttendanceSchedule.findAttendanceScheduleByLocalDate(todayDate);
        return Arrays.stream(AttendanceStatus.values())
                .sorted(Comparator.comparingInt(AttendanceStatus::getThresholdMinutes).reversed())
                .filter(status -> attendanceTime.isAfter(attendanceStartTime.plusMinutes(status.getThresholdMinutes())))
                .findFirst()
                .orElse(AttendanceStatus.ATTENDANCE);
    }

    public static long calculateAttendanceStatusCount(Map<LocalDate, LocalTime> attendanceRecord,
                                                      AttendanceStatus attendanceStatus) {
        return attendanceRecord.entrySet().stream()
                .filter(entry -> AttendanceStatus.calculateAttendanceStatus(
                        entry.getKey(),
                        entry.getValue()).equals(attendanceStatus))
                .count();
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public int getThresholdMinutes() {
        return thresholdMinutes;
    }
}
