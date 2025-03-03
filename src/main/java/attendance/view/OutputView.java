package attendance.view;

import attendance.domain.Attendance;
import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceRecords;
import attendance.domain.AttendanceUpdate;

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

        System.out.printf("%d월 %d일 %s %s (%s)",
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                dateTime.toLocalTime(),
                stateView.getName()
        );
    }

    public void printAttendanceUpdate(AttendanceUpdate attendanceUpdate) {
        printAttendanceResult(attendanceUpdate.getBeforeAttendance());
        Attendance attendance = attendanceUpdate.getAfterAttendance();
        System.out.printf(" -> %s (%s) 수정 완료!",
                attendance.getDateTime().toLocalTime(),
                AttendanceStateView.findByName(attendance.getState().name())
        );
    }

    public void printAttendanceRecord(AttendanceRecord attendanceRecord) {
        AttendanceRiskView risk = AttendanceRiskView.findByName(attendanceRecord.getStatus().getRisk().name());
        System.out.printf("\n이번 달 %s의 출석 기록입니다.\n\n", attendanceRecord.getNickname());
        attendanceRecord.getRecord().getAttendances()
                .forEach(attendance -> {
                    this.printAttendanceResult(attendance);
                    System.out.println();
                });

        System.out.printf("""
                        
                        출석: %d회
                        지각: %d회
                        결석: %d회
                        
                        %s 대상자입니다.
                        """,
                attendanceRecord.getStatus().getAttendanceStateCount(),
                attendanceRecord.getStatus().getTardyStateCount(),
                attendanceRecord.getStatus().getAbsenceStateCount(),
                risk.getName()
        );
    }

    public void printRiskCrewsSearch(AttendanceRecords attendanceRecords) {
        System.out.println("\n제적 위험자 조회 결과");
        attendanceRecords.records()
                .forEach(this::printRiskCrewSearch);
    }

    private void printRiskCrewSearch(AttendanceRecord attendanceRecord) {
        AttendanceRiskView risk = AttendanceRiskView.findByName(attendanceRecord.getStatus().getRisk().name());
        System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                attendanceRecord.getNickname(),
                attendanceRecord.getStatus().getAbsenceStateCount(),
                attendanceRecord.getStatus().getTardyStateCount(),
                risk.getName()
        );
    }
}
