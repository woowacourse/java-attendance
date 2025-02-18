package view;

import java.time.LocalDateTime;

public class OutputView {
    private final String RECORD_FORMAT = "%d월 %02d일 %s %02d:%02d (출석)%n";

    public void printAttendanceRecord(LocalDateTime dateAndTime){
        System.out.printf(RECORD_FORMAT,dateAndTime.getMonth(),dateAndTime.getDayOfMonth(),
            dateAndTime.getDayOfWeek(), dateAndTime.getHour(), dateAndTime.getMinute());
    }

}
