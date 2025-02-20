package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class Time {
    private final LocalDate date;
    private final String hour;
    private final String minute;
    private final boolean isAbsent;

    public Time(LocalDate date, String hour, String minute, boolean isAbsent) {
        if (!isAbsent) {
            validatePossibleTime(date, hour, minute);
            validateInRangeTime(hour, minute);
        }
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        this.isAbsent = isAbsent;
    }

    private void validateInRangeTime(String hour, String minute) {
        try {
            if (Integer.parseInt(hour) >= 24 || Integer.parseInt(hour) < 0 || Integer.parseInt(minute) >= 60
                    || Integer.parseInt(minute) < 0) {
                throw new IllegalArgumentException("[ERROR] 올바른 시간을 입력해주세요.");

            }
        } catch (NumberFormatException e) {
            System.out.println(hour);
            System.out.println(minute);
            throw new IllegalArgumentException("[ERROR] 올바른 시간을 입력해주세요.");
        }
    }


    private void validatePossibleTime(LocalDate date, String hour, String minute) {
        String day = date.getDayOfWeek().name();

        if (day.equals("SATURDAY") || day.equals("SUNDAY")) {
            String message = String.format("[ERROR] %02d월 %02d일 %s은 등교일이 아닙니다.",
                    date.getMonthValue(), date.getDayOfMonth(),
                    date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
            throw new IllegalArgumentException(message);
        }

        if (Integer.parseInt(hour) < 8 || (Integer.parseInt(hour) == 23 && Integer.parseInt(minute) > 0)) {
            throw new IllegalArgumentException("[ERROR] 출석 가능한 시간이 아닙니다.");
        }
    }

    public boolean isAfter(LocalDateTime localDateTime) {
        if (!isAbsent) {
            return date.atTime(Integer.parseInt(hour), Integer.parseInt(minute)).isAfter(localDateTime);
        }

        return true;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getYear() {
        return date.getYear();
    }

    public int getMonth() {
        return date.getMonthValue();
    }

    public int getDay() {
        return date.getDayOfMonth();
    }

    public String getDayOfWeek() {
        return date.getDayOfWeek().getDisplayName(
                TextStyle.FULL, Locale.KOREAN);
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public boolean isAbsent() {
        return isAbsent;
    }
}
