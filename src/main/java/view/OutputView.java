package view;

import domain.Attend;
import domain.AttendStatus;

public class OutputView {
    public void printAttendResult(final Attend attend, final AttendStatus attendStatus) {
        String dateFormat = DateTimeFormat.DATE.formatDate(attend.getDate());
        String timeFormat = DateTimeFormat.TIME.formatTime(attend.getTime());
        String attendStatusFormat = AttendStatusFormat.findStatusFormat(attendStatus);
        System.out.printf("%s %s (%s)%n%n", dateFormat, timeFormat, attendStatusFormat);
    }
}
