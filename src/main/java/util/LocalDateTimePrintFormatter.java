package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.AttendanceCalculatorByDay;

public class LocalDateTimePrintFormatter {
    public static String LocalDateTimeToLocalTime(LocalDateTime localDateTime) {
        int month = localDateTime.getMonthValue();
        int date = localDateTime.getDayOfMonth();
        int dayOfWeek = localDateTime.getDayOfWeek().getValue();
        String day = AttendanceCalculatorByDay.findDayByDayOfWeekValue(dayOfWeek);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String time = localDateTime.format(dateTimeFormatter);
        if (time.equals("00:00")){
            time = "--:--";
        }
        return month + "월 " + date + "일 " + day +" "+ time + " ";
    }
}
