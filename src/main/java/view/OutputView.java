package view;

import domain.TimeAndStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {

    private final String RECORD_FORMAT = "%s %s%n";
    private final String EDIT_FORMAT = "%s %s -> %s 수정 완료!";
    private final String DATE_FORMAT = "%d월 %02d일 %s";
    private final String TIME_FORMAT = "%02d:%02d (%s)";

    public void printAttendanceRecord(LocalDate localDate, TimeAndStatus timeAndStatus) {
        String date = String.format(DATE_FORMAT, localDate.getMonthValue(),
            localDate.getDayOfMonth(),
            localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
        String time = String.format(TIME_FORMAT, timeAndStatus.getTime().getHour(), timeAndStatus.getTime().getMinute(), timeAndStatus.getStatus());

        System.out.printf(RECORD_FORMAT, date, time);
    }

    public void printEditResult(LocalDate localDate, TimeAndStatus before, TimeAndStatus after) {
        String date = String.format(DATE_FORMAT, localDate.getMonthValue(),
            localDate.getDayOfMonth(),
            localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
        String beforeInfo = String.format(TIME_FORMAT, before.getTime().getHour(),
            before.getTime().getMinute(), before.getStatus());
        String afterInfo = String.format(TIME_FORMAT, after.getTime().getHour(),
            after.getTime().getMinute(), after.getStatus());

        System.out.printf(EDIT_FORMAT, date, beforeInfo, afterInfo);
    }

}
