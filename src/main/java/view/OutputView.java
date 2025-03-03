package view;

import controller.DateTimeConverter;
import domain.attendance.AttendanceDate;
import domain.attendance.AttendanceWarning;
import domain.attendance.EmptyAttendanceDateException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OutputView {
    public void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    public void printAttend(LocalDateTime attendedTime, String attendanceStatus) {
        System.out.println(DateTimeConverter.convertLocalDateToString(attendedTime.toLocalDate()) + " "
                + DateTimeConverter.convertLocalTimeToString(attendedTime.toLocalTime())
                + " (" + attendanceStatus + ")");
    }

    public void printEdit(LocalDateTime beforeDateTime,
                          String beforeStatus,
                          LocalDateTime afterDateTime,
                          String afterStatus) {
        System.out.println(DateTimeConverter.convertLocalDateToString(beforeDateTime.toLocalDate()) + " "
                + DateTimeConverter.convertLocalTimeToString(beforeDateTime.toLocalTime())
                + " (" + beforeStatus + ") -> "
                + DateTimeConverter.convertLocalTimeToString(afterDateTime.toLocalTime())
                + " (" + afterStatus + ") 수정 완료!");
    }

    public void printCrewInfo(String nickname, List<AttendanceDate> attendanceDates) {
        System.out.println("이번 달 " + nickname + "의 출석 기록입니다.\n");
        ArrayList<AttendanceDate> sortedAttendanceDates = new ArrayList<>(attendanceDates);
        sortedAttendanceDates.sort(Comparator.comparing(AttendanceDate::getDate));
        sortedAttendanceDates.forEach(this::printAttendanceDate);
        System.out.println();
    }

    private void printAttendanceDate(AttendanceDate attendanceDate) {
        System.out.print(DateTimeConverter.convertLocalDateToString(attendanceDate.getDate()) + " ");
        try {
            System.out.print(DateTimeConverter.convertLocalTimeToString(attendanceDate.getDateTime().toLocalTime()));
        } catch (EmptyAttendanceDateException exception) {
            System.out.print("--:--");
        }
        System.out.println(" (" + attendanceDate.getStatus().getStatus() + ")");
    }

    public void printCrewWarningStatus(int countAttendance, int countTardy, int countAbsence, int warningStatus) {
        System.out.println("출석: " + countAttendance +
                "\n지각: " + countTardy +
                "\n결석: " + countAbsence + "\n");
        if (warningStatus >= AttendanceWarning.WARNING.getAbsenceCount()) {
            System.out.println(getWarningStatus(warningStatus) + " 대상자입니다");
        }
    }

    private String getWarningStatus(int warningStatus) {
        if (AttendanceWarning.EXPELLED.getAbsenceCount() <= warningStatus) {
            return "제적";
        }
        if (AttendanceWarning.INTERVIEW.getAbsenceCount() <= warningStatus) {
            return "면담";
        }
        return "경고";
    }
}
