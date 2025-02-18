package view;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {
    private final String RECORD_FORMAT = "%d월 %02d일 %s %02d:%02d (출석)%n";

    public void printAttendanceRecord(LocalDateTime dateAndTime){
        System.out.printf(RECORD_FORMAT,dateAndTime.getMonthValue(), dateAndTime.getDayOfMonth(),
        dateAndTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
            dateAndTime.getHour(), dateAndTime.getMinute());
    }

}
