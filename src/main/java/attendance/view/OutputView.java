package attendance.view;

import attendance.domain.Attendance;
import attendance.domain.AttendanceRiskCrews;
import attendance.domain.AttendanceState;
import attendance.domain.AttendanceStatus;
import attendance.domain.AttendanceUpdate;
import attendance.domain.Attendances;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public class OutputView {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String OUTPUT_MENU = NEW_LINE + NEW_LINE + """
            오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료
            """;

    public void printMenu(LocalDate today) {
        System.out.printf(OUTPUT_MENU,
                today.getMonthValue(),
                today.getDayOfMonth(),
                today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
        );
    }

    public void printAttendanceRecord(Attendance attendance) {
        LocalDateTime dateTime = attendance.getDateTime();

        String timeContent = dateTime.toLocalTime().toString();
        if (dateTime.toLocalTime().equals(LocalTime.MAX)) {
            timeContent = "--:--";
        }

        System.out.printf(NEW_LINE + "%d월 %d일 %s %s (%s)",
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                timeContent,
                attendance.getState().getName()
        );
    }

    public void printAttendUpdateResult(AttendanceUpdate update) {
        printAttendanceRecord(update.getBeforeAttendance());
        System.out.printf(" -> %s (%s) 수정 완료!",
                update.getAfterAttendance().getDateTime().toLocalTime(),
                update.getAfterAttendance().getState().getName()
        );
    }

    public void printAttendanceRecords(Attendances attendances, String nickname) {
        System.out.printf(NEW_LINE + "이번 달 %s의 출석 기록입니다." + NEW_LINE, nickname);

        attendances.getAttendances().forEach(this::printAttendanceRecord);
        System.out.println();
    }

    public void printAttendanceStatus(final AttendanceStatus attendanceStatus) {
        EnumMap<AttendanceState, Integer> status = attendanceStatus.getStatus();

        for (Map.Entry<AttendanceState, Integer> entry : status.entrySet()) {
            System.out.printf(NEW_LINE + "%s: %d회",
                    entry.getKey().getName(),
                    entry.getValue()
            );
        }

        System.out.println();
        System.out.printf(NEW_LINE + "%s 대상자입니다.", attendanceStatus.getRisk().getName());
    }

    public void printAttendanceRiskCrews(final AttendanceRiskCrews riskCrews) {
        System.out.print(NEW_LINE + "제적 위험자 조회 결과");

        for (Map.Entry<String, AttendanceStatus> statusEntry : riskCrews.getRiskCrews().entrySet()) {
            System.out.printf("\n- %s: 결석 %d회, 지각 %d회 (%s)",
                    statusEntry.getKey(),
                    statusEntry.getValue().getStatus().get(AttendanceState.ABSENCE),
                    statusEntry.getValue().getStatus().get(AttendanceState.LATE),
                    statusEntry.getValue().getRisk().getName()
            );
        }
    }
}
