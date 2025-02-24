package util;

import constant.DateFormatInformation;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import model.AttendanceRecord;

public class LocalDateTimePrintFormatter {

    public static String LocalDateTimeToLocalTime(LocalDate localDate, AttendanceRecord attendanceRecord) {
        if (attendanceRecord.getAttendanceTime() == null) {
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            return localDate.format(DateTimeFormatter.ofPattern("MM월 dd일 " + dayOfWeek.getDisplayName(
                    TextStyle.FULL, Locale.KOREAN) + " --:--"));
        }

        LocalDateTime localDateTime = LocalDateTime.of(localDate, attendanceRecord.getAttendanceTime());
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        return localDateTime.format(DateTimeFormatter.ofPattern("MM월 dd일 " + dayOfWeek.getDisplayName(
                TextStyle.FULL, Locale.KOREAN) + " " + DateFormatInformation.LOCAL_TIME_FORMATTER));
    }


}
