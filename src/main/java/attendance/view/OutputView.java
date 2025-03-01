package attendance.view;

import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;
import java.time.format.DateTimeFormatter;

public class OutputView {


    public void printAttendance(Attendance attendance) {
        printEmptyLine();
        System.out.printf(
                "%s (%s)\n",
                attendance.getAttendanceDateTime().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm")),
                toKoreaAttendanceStatus(attendance.getAttendanceStatus())
        );
    }

    private void printEmptyLine() {
        System.out.println();
    }

    public String toKoreaAttendanceStatus(AttendanceStatus attendanceStatus) {
        return switch (attendanceStatus) {
            case ATTENDANCE -> "출석";
            case LATE -> "지각";
            case ABSENT -> "결석";
        };
    }
}
