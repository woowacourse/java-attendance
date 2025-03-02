import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class AttendancePolicy {

    private static final int SATURDAY_VALUE = 6;
    private static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23, 0);
    private static final LocalTime LATE_TIME = LocalTime.of(10, 5);
    private static final LocalTime ABSENCE_TIME = LocalTime.of(10, 30);

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
        if (attendanceTime.toLocalTime().isAfter(ABSENCE_TIME)) {
            return AttendanceStatus.ABSENCE;
        }
        if (attendanceTime.toLocalTime().isAfter(LATE_TIME)) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }
}
