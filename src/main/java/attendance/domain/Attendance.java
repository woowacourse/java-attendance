package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Attendance {
    private final String name;
    private final Map<LocalDate, HourMinute> timestamps;

    public Attendance(String name) {
        this.name = name;
        this.timestamps = new HashMap<>();
    }

    public void add(final LocalDateTime localDateTime) {
        LocalDate date = localDateTime.toLocalDate();
        HourMinute hourMinute = new HourMinute(localDateTime.getHour(), localDateTime.getMinute());

        if (timestamps.containsKey(date)) {
            throw new IllegalArgumentException("[ERROR] 출석 기록이 존재합니다. 출석 수정 기능을 이용하세요.");
        }

        timestamps.put(date, hourMinute);
    }

    public static int checkAttendance(final int hour, final int minute) {
        if (hour <= 10 && minute <= 5) {
            return 1;
        }
        if (hour == 10 && (minute <= 30)) {
            return 0;
        }
        return -1;
    }

    public static int checkMondayAttendance(final int hour, final int minute) {
        if (hour <= 13 && minute <= 5) {
            return 1;
        }
        if (hour == 13 && (minute <= 30)) {
            return 0;
        }
        return -1;
    }

    public static void method1(final int hour, final int minute) {
        if (hour < 8 || (hour >= 23 && minute > 0)) {
            throw new IllegalArgumentException("[ERROR] 현재 캠퍼스 운영시간이 아닙니다.");
        }
    }

    public static void method2(final int day) {
        LocalDate localDate = LocalDate.of(2024, 12, day);
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.", localDate.getMonthValue(), day,
                            dayOfWeek.getDisplayName(
                                    TextStyle.FULL, Locale.KOREA)));
        }

        if(day == 25){
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.", localDate.getMonthValue(), day,
                            dayOfWeek.getDisplayName(
                                    TextStyle.FULL, Locale.KOREA)));
        }
    }
}
