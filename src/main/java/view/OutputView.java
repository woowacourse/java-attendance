package view;

import domain.Attend;
import domain.AttendStatus;
import java.time.LocalTime;

public class OutputView {
    public void printAttendResult(final Attend attend, final AttendStatus attendStatus) {
        String dateFormat = DateTimeFormat.DATE.formatDate(attend.getDate());
        String timeFormat = formatAttendTime(attend);
        String attendStatusFormat = AttendStatusFormat.findStatusFormat(attendStatus);
        System.out.printf("%s %s (%s)%n%n", dateFormat, timeFormat, attendStatusFormat);
    }

    private String formatAttendTime(Attend attend) {
        String result = "--:--";
        if (attend.checkTimeNull()) {
            LocalTime time = attend.getTime();
            result = DateTimeFormat.TIME.formatTime(time);
        }
        return result;
    }
}
