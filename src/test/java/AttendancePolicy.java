import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class AttendancePolicy {

    public static final int MONDAY_VALUE = 1;
    private static final int SATURDAY_VALUE = 6;
    private static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23, 0);
    private static final LocalTime LATE_TIME = LocalTime.of(10, 5);
    private static final LocalTime ABSENCE_TIME = LocalTime.of(10, 30);
    private static final LocalTime MONDAY_LATE_TIME = LocalTime.of(13, 5);
    private static final LocalTime MONDAY_ABSENCE_TIME = LocalTime.of(13, 30);

    public void validateIsWeekDays(LocalDate today) {
        if (today.getDayOfWeek().getValue() >= SATURDAY_VALUE) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %02d월 %02d일 %s은 등교일이 아닙니다.",
                            today.getMonthValue(), today.getDayOfMonth(),
                            today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)));
        }
    }

    public void validateCampusOpen(LocalTime todayTime) {
        if (todayTime.isBefore(CAMPUS_OPEN_TIME) || todayTime.isAfter(CAMPUS_CLOSE_TIME)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }

    public AttendanceStatus getAttendanceStatus(LocalDateTime attendanceTime) {
        LocalTime lateTime = getLateTime(attendanceTime);
        LocalTime absenceTime = getAbsenceTime(attendanceTime);
        LocalTime currentTime = attendanceTime.toLocalTime();

        if (currentTime.isAfter(absenceTime)) {
            return AttendanceStatus.ABSENCE;
        }
        if (currentTime.isAfter(lateTime)) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }

    private boolean isMonday(LocalDateTime attendanceTime) {
        return attendanceTime.getDayOfWeek().getValue() == MONDAY_VALUE;
    }

    private LocalTime getLateTime(LocalDateTime attendanceTime) {
        return isMonday(attendanceTime) ? MONDAY_LATE_TIME : LATE_TIME;
    }

    private LocalTime getAbsenceTime(LocalDateTime attendanceTime) {
        return isMonday(attendanceTime) ? MONDAY_ABSENCE_TIME : ABSENCE_TIME;
    }

}
