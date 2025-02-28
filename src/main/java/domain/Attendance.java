package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class Attendance {

    private final static LocalTime OPEN_TIME = LocalTime.of(8, 0);
    private final static LocalTime CLOSE_TIME = LocalTime.of(23, 0);
    private final static LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);
    private final static String ATTENDANCE_START_1PM_DAY = "월요일";
    private final static List<String> ATTENDANCE_START_10AM_DAYS = List.of("화요일", "수요일", "목요일", "금요일");

    private final LocalDate date;
    private final LocalTime time;

    public Attendance(LocalDate date, LocalTime time) {
        validateDate(date);
        validateTime(time);
        this.date = date;
        this.time = time;
    }

    public boolean hasSameDate(LocalDate date) {
        return this.date.equals(date);
    }

    public String determineStatus() {
        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        if (dayOfWeek.equals(ATTENDANCE_START_1PM_DAY)) {
            if (time.isAfter(LocalTime.of(13, 5)) && time.isBefore(LocalTime.of(13, 31))) {
                return "지각";
            }
            if (time.isAfter(LocalTime.of(13, 30))) {
                return "결석";
            }
            return "출석";
        }
        if (ATTENDANCE_START_10AM_DAYS.contains(dayOfWeek)) {
            if (time.isAfter(LocalTime.of(10, 5)) && time.isBefore(LocalTime.of(10, 31))) {
                return "지각";
            }
            if (time.isAfter(LocalTime.of(10, 30))) {
                return "결석";
            }
            return "출석";
        }
        return null;
    }

    private void validateTime(LocalTime time) {
        if (time.isBefore(OPEN_TIME) || time.isAfter(CLOSE_TIME)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }

    private void validateDate(LocalDate date) {
        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        if (dayOfWeek.equals("토요일") || dayOfWeek.equals("일요일") || date.equals(CHRISTMAS)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d월 %02d일 %s은(는) 등교일이 아닙니다.", date.getMonthValue(), date.getDayOfMonth(),
                            dayOfWeek));
        }
    }
}
