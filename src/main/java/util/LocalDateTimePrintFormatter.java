package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class LocalDateTimePrintFormatter {

    public static final DateTimeFormatter dateTimeFormatterForHourMin = DateTimeFormatter.ofPattern("HH:mm");

    public static String LocalDateTimeToLocalTime(LocalDateTime localDateTime) {
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();

        if (localDateTime.format(dateTimeFormatterForHourMin).equals("00:00")) {
            return localDateTime.format(DateTimeFormatter.ofPattern("MM월 dd일 " + dayOfWeek.getDisplayName(
                    TextStyle.FULL, Locale.KOREAN) + " --:--"));
        }

        return localDateTime.format(DateTimeFormatter.ofPattern("MM월 dd일 " + dayOfWeek.getDisplayName(
                TextStyle.FULL, Locale.KOREAN) + " HH:mm"));
    }

    public static String isNotAttendanceAvailable(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();

        String formattedDate = localDate.format(DateTimeFormatter.ofPattern("MM월 dd일"));

        return "[ERROR] " + formattedDate + " " +
                dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN) +
                "은 등교일이 아닙니다.";
    }

    public static String modifyComplete(LocalDateTime modifyDateTime, String recordAfterModifyState) {
        return modifyDateTime.format(dateTimeFormatterForHourMin) + " (" +recordAfterModifyState + ") 수정 완료!";
    }


}
