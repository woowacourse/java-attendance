package view;

import domain.AttendanceDto;
import domain.DayOfWeek;
import util.Converter;

import java.time.LocalDate;

public class OutputView {
    private static final String ATTENDANCE_HISTORY_MESSAGE = "이번 달 %s의 출석 기록입니다.";
    private static final String APPLICATION_START_MESSAGE = "오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.\n" +
            "1. 출석 확인\n" +
            "2. 출석 수정\n" +
            "3. 크루별 출석 기록 확인\n" +
            "4. 제적 위험자 확인\n" +
            "Q. 종료\n";


    public void printOptionMessage() {
        LocalDate today = LocalDate.now();
        String dayOfWeekName = DayOfWeek.getNameById(today.getDayOfWeek().getValue());
        System.out.printf(APPLICATION_START_MESSAGE, today.getMonth().getValue(), today.getDayOfMonth(), dayOfWeekName);
    }

    public void printAttendanceInformation(AttendanceDto attendanceDto) {
        LocalDate today = LocalDate.now();
        String dayOfWeekName = DayOfWeek.getNameById(today.getDayOfWeek().getValue());
        String attendanceTime = Converter.covertLocalTimeToString(attendanceDto.getAttendanceTime());

        String attendanceStatusName = "출석";
        if (attendanceDto.getAbsent()) {
            attendanceStatusName = "결석";
        }

        if (attendanceDto.getLate()) {
            attendanceStatusName = "지각";
        }
        System.out.printf("%d월 %02d일 %s %s (%s)\n", today.getMonth().getValue(), today.getDayOfMonth(), dayOfWeekName, attendanceTime, attendanceStatusName);
    }

}
