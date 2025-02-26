package attendance.view;

import attendance.domain.Attendance;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {
    public static void printNotOperationDate(final LocalDate attendanceDate) {
        int month = attendanceDate.getMonthValue();
        int date = attendanceDate.getDayOfMonth();
        DayOfWeek day = attendanceDate.getDayOfWeek();
        String dayName = day.getDisplayName(TextStyle.FULL, Locale.KOREAN);
        System.out.printf("[ERROR] %02d월 %02d일 %s은 등교일이 아닙니다.\n", month, date, dayName);
    }

    public static void printNotRegisteredCrewNickname() {
        System.out.println("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    public static void printNotOperationTime() {
        System.out.println("[ERROR] 캠퍼스 운영 시간은 매일 08:00 ~ 23:00 입니다.");
    }

    public static void printDuplicatedAttendance() {
        System.out.println("[ERROR] 이미 출석하셨습니다.");
    }

    public static void printAttendance(final Attendance attendance) {
        LocalDate attendanceDate = attendance.getDate();
        int month = attendanceDate.getMonthValue();
        int date = attendanceDate.getDayOfMonth();
        DayOfWeek day = attendanceDate.getDayOfWeek();
        String dayName = day.getDisplayName(TextStyle.FULL, Locale.KOREAN);
        LocalTime attendanceTime = attendance.getTime();
        int hour = attendanceTime.getHour();
        int minute = attendanceTime.getMinute();
        String status = attendance.getStatus();
        System.out.printf("%02d월 %02d일 %s %02d:%02d (%s)\n", month, date, dayName, hour, minute, status);
    }
}
