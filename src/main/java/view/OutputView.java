package view;

import domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import util.Convertor;

public class OutputView {

    public void printMenuHeader(LocalDate nowDate) {
        System.out.print(String.format("오늘은 %d월 %d일 %s요일입니다. 기능을 선택해 주세요.", nowDate.getMonthValue(), nowDate.getDayOfMonth(),
                Convertor.convertDayOfWeekToKorean(nowDate.getDayOfWeek())));
    }

    public void printAttendanceMessage(LocalDateTime attendanceDateTime, AttendanceStatus attendanceStatus) {
        System.out.println(String.format("%n%d월 %02d일 %s요일 %02d:%02d (%s)",
                attendanceDateTime.getMonthValue(),
                attendanceDateTime.getDayOfMonth(),
                Convertor.convertDayOfWeekToKorean(attendanceDateTime.getDayOfWeek()),
                attendanceDateTime.getHour(),
                attendanceDateTime.getMinute(),
                attendanceStatus.getStatus()));
    }
}
