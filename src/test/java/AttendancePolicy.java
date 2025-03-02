import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class AttendancePolicy {

    private static final int SATURDAY_VALUE = 6;
    private static final int CAMPUS_OPEN_TIME = 8;
    private static final int CAMPUS_CLOSE_TIME = 23;

    public void validateIsWeekDays(LocalDate today) {
        if (today.getDayOfWeek().getValue() >= SATURDAY_VALUE) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %02d월 %02d일 %s은 등교일이 아닙니다.",
                            today.getMonthValue(), today.getDayOfMonth(),
                            today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)));
        }
    }

    public void validateCampusOpen(LocalTime todayTime) {
        if (todayTime.isBefore(LocalTime.of(CAMPUS_OPEN_TIME, 0))
                || todayTime.isAfter(LocalTime.of(CAMPUS_CLOSE_TIME, 0))) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }
}
