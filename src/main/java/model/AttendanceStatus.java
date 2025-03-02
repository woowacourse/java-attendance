package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;

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
        LocalTime attendanceStartTime = WeeklyAttendanceSchedule.findAttendanceScheduleByLocalDate(todayDate);
        return Arrays.stream(AttendanceStatus.values())
                .sorted(Comparator.comparingInt(AttendanceStatus::getThresholdMinutes).reversed())
                .filter(status -> attendanceTime.isAfter(attendanceStartTime.plusMinutes(status.getThresholdMinutes())))
                .findFirst()
                .orElse(AttendanceStatus.ATTENDANCE);
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public int getThresholdMinutes() {
        return thresholdMinutes;
    }
}
