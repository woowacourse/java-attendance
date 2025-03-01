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

    public void printEditResult(final Attend before, final AttendStatus beforeStatus,
                                final Attend after, final AttendStatus afterStatus) {
        String date = DateTimeFormat.DATE.formatDate(before.getDate());
        String beforeTime = formatAttendTime(before);
        String afterTime = formatAttendTime(after);
        String beforeStatusFormat = AttendStatusFormat.findStatusFormat(beforeStatus);
        String afterStatusFormat = AttendStatusFormat.findStatusFormat(afterStatus);
        System.out.printf("%s %s (%s) -> %s (%s) 수정 완료!%n%n",
                date, beforeTime, beforeStatusFormat, afterTime, afterStatusFormat);
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
