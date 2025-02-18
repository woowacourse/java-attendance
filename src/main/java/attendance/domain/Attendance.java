package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class Attendance {
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
