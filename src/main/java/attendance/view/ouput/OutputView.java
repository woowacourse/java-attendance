package attendance.view.ouput;

import attendance.domain.AttendanceDate;
import attendance.domain.AttendanceDateTime;
import attendance.domain.AttendanceStatus;
import attendance.domain.AttendanceTime;

public class OutputView {

    public void printMessage(final String message) {
        System.out.println(message);
    }

    public void printAttendanceDateTime(
        final AttendanceDateTime attendanceDateTime,
        final AttendanceStatus attendanceStatus
    ) {
        final AttendanceDate attendanceDate = attendanceDateTime.getAttendanceDate();
        final AttendanceTime attendanceTime = attendanceDateTime.getAttendanceTime();

        System.out.printf("%s월 %s일 %s요일 %s:%s (%s)\n",
            attendanceDate.getMonth(),
            attendanceDate.getDay(),
            attendanceDate.getAttendanceDayOfWeekDayOfWeek()
                .getTitle(),
            convertTime(attendanceTime.getHour()
                .orElse(null)),
            convertTime(attendanceTime.getMinute()
                .orElse(null)),
            attendanceStatus.getTitle());
    }

    private String convertTime(final Integer time) {
        if (time == null) {
            return "--";
        }

        final String rawTime = String.valueOf(time);
        if (rawTime.length() < 2) {
            return "0" + rawTime;
        }

        return rawTime;
    }
}
