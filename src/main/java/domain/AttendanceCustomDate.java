package domain;

import java.time.LocalDateTime;

public class AttendanceCustomDate {
    public static final int YEAR = 2024;
    public static final Month MONTH = Month.DECEMBER;

    public static LocalDateTime now() {
        LocalDateTime now = LocalDateTime.now();
        // TODO: DayOfMonth를 동적으로 설정할 수 있도록 개선하기
        return now.withYear(YEAR).withMonth(MONTH.getValue()).withDayOfMonth(13);
    }
}
