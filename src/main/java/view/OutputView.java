package view;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {
    private final String RECORD_FORMAT = "%s %s%n";
    private final String EDIT_FORMAT = "%s -> %s 수정 완료!";
    private final String DATE_FORMAT = "%d월 %02d일 %s";
    private final String TIME_FORMAT = "%02d:%02d (출석)";

    public void printAttendanceRecord(LocalDateTime dateAndTime){
        String date = String.format(DATE_FORMAT, dateAndTime.getMonthValue(), dateAndTime.getDayOfMonth(),
            dateAndTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
        String time = String.format(TIME_FORMAT, dateAndTime.getHour(), dateAndTime.getMinute());

        System.out.printf(RECORD_FORMAT, date, time);
    }

    public void printEditResult(LocalDateTime beforeTime, LocalDateTime afterTime){
        String date = String.format(DATE_FORMAT, dateAndTime.getMonthValue(), dateAndTime.getDayOfMonth(),
            dateAndTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
        String time = String.format(TIME_FORMAT, dateAndTime.getHour(), dateAndTime.getMinute());


    }

}
