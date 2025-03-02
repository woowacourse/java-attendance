package attendance.view;

import attendance.domain.Attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {

    public void printMenu(LocalDate today) {
        System.out.printf("""
                        오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.
                        1. 출석 확인
                        2. 출석 수정
                        3. 크루별 출석 기록 확인
                        4. 제적 위험자 확인
                        Q. 종료
                        """,
                today.getMonthValue(),
                today.getDayOfMonth(),
                today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
        );
    }

    public void printAttendanceResult(Attendance attendance) {
        LocalDateTime dateTime = attendance.getDateTime();
        AttendanceStateView stateView = AttendanceStateView.findByName(attendance.getState().name());

        System.out.printf("%d월 %d일 %s %s (%s)\n",
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                dateTime.toLocalTime(),
                stateView.getName()
        );
    }
}
