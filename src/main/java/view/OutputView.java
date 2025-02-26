package view;

import domain.Attendance;
import domain.AttendanceDate;
import domain.AttendanceStatus;
import domain.AttendanceTime;
import java.time.LocalDate;
import util.DayOfWeekConvertor;

public class OutputView {

    public void printWelcomeMessage(LocalDate nowDate) {
        System.out.println(String.format("오늘은 %d월 %d일 %s요일입니다. 기능을 선택해 주세요.",
                nowDate.getMonthValue(),
                nowDate.getDayOfMonth(),
                DayOfWeekConvertor.convertToKorean(nowDate.getDayOfWeek())));
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void printAttendanceMessage(Attendance attendance) {
        System.out.println(this.generateAttendanceMessage(attendance) + System.lineSeparator());
    }

    public void printEditMessage(Attendance oldAttendance, Attendance newAttendance) {
        System.out.print(System.lineSeparator() + generateAttendanceMessage(oldAttendance));

        System.out.println(String.format(" -> %02d:%02d (%s) 수정 완료!",
                newAttendance.getAttendanceTime().getHour(),
                newAttendance.getAttendanceTime().getMinute(),
                newAttendance.getAttendanceStatus().getStatus()));
    }

    private String generateAttendanceMessage(Attendance attendance) {
        AttendanceDate attendanceDate = attendance.getAttendanceDate();
        AttendanceTime attendanceTime = attendance.getAttendanceTime();
        AttendanceStatus attendanceStatus = attendance.getAttendanceStatus();

        return String.format("%d월 %d일 %s요일 %02d:%02d (%s)",
                attendanceDate.getMonthValue(),
                attendanceDate.getDayOfMonth(),
                DayOfWeekConvertor.convertToKorean(attendanceDate.getDayOfWeek()),
                attendanceTime.getHour(),
                attendanceTime.getMinute(),
                attendanceStatus.getStatus());
    }
}
