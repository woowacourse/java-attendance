package attendance.view;

import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class OutputView {


    public void printAttendance(Attendance attendance) {
        printEmptyLine();
        System.out.println(toAttendanceFullFormat(attendance));
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

    public void printUpdatedAttendance(Optional<Attendance> pastAttendance, Attendance currentAttendance) {
        pastAttendance.ifPresentOrElse(
                attendance -> System.out.printf("%s -> %s 수정 완료!\n",
                        toAttendanceFullFormat(attendance),
                        toAttendanceShortFormat(currentAttendance)),
                () -> System.out.printf("%s -> %s 수정 완료!\n",
                        toAbsentFullFormat(currentAttendance.getAttendanceDateTime().toLocalDate()),
                        toAttendanceShortFormat(currentAttendance))
        );
    }

    private String toAttendanceFullFormat(Attendance attendance) {
        return String.format("%s (%s)",
                attendance.getAttendanceDateTime().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm")),
                toKoreaAttendanceStatus(attendance.getAttendanceStatus()));
    }

    private String toAttendanceShortFormat(Attendance attendance) {
        return String.format("%s (%s)",
                attendance.getAttendanceDateTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                toKoreaAttendanceStatus(attendance.getAttendanceStatus()));
    }

    private String toAbsentFullFormat(LocalDate date) {
        return String.format("%s (결석)", date.format(DateTimeFormatter.ofPattern("MM월 dd일 E요일 --:--")));
    }
}
