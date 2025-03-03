package view;

import domain.AttendanceState;
import domain.Calender;
import domain.DateProvider;
import java.time.LocalDateTime;

public class OutputView {

    public void printWellComeMessage(final DateProvider dateProvider) {

        String wellComeMessageFormat = String.format(
                "\n오늘은 %02d월 %02d일 %s입니다. 기능을 선택해 주세요.",
                dateProvider.getMonth(),
                dateProvider.getDayOfMonth(),
                dateProvider.getDayOfWeek().getDescription());

        System.out.println(wellComeMessageFormat);
    }

    public void printAttendanceRecord(final LocalDateTime attendanceDateTime, final AttendanceState attendanceState) {
        String attendanceRecordFormat = String.format(
                "\n%02d월 %02d일 %s %02d:%02d (%s)",
                attendanceDateTime.getMonth().getValue(),
                attendanceDateTime.getDayOfMonth(),
                Calender.findBy(attendanceDateTime).getDescription(),
                attendanceDateTime.getHour(),
                attendanceDateTime.getMinute(),
                attendanceState.getState());

        System.out.println(attendanceRecordFormat);

    }

    public void printExit() {
        System.out.println("프로그램을 종료합니다.");
    }
}
