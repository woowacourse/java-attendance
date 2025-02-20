package attendance.view;

import attendance.dto.AttendanceGroupByStatus;
import attendance.dto.AttendanceResponse;
import attendance.dto.AttendanceSearchResult;
import attendance.dto.AttendanceUpdateResult;
import attendance.dto.WarnedStudentResponse;
import attendance.dto.WarnedStudentResponses;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputView { // todo : 상수 분리 적용 필요, response 파라미터 네이밍 통일, 메서드 순서 정렬

    private static final String OUTPUT_MENU = """
            오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료
            """;

    public void printMenu(LocalDate today) { // todo : 파라미터가 today 적절한가?
        System.out.printf(OUTPUT_MENU,
                today.getMonthValue(),
                today.getDayOfMonth(),
                today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA) // todo : 요일 반환 메서드 분리 필요
        );
    }

    public void printAttendanceRecord(AttendanceResponse response) {
        LocalDateTime dateTime = response.dateTime();

        String timeContent = dateTime.toLocalTime().toString(); // todo : 파싱 메서드 분리 고려
        if (dateTime.toLocalTime().equals(LocalTime.MIN)) {
            timeContent = "--:--";
        }

        System.out.printf("%d월 %d일 %s %s (%s)\n",
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                timeContent,
                response.status()
        );
    }

    public void printAttendUpdateResult(AttendanceUpdateResult result) {
        printAttendanceRecord(result.before());
        System.out.printf(" -> %s (%s) 수정 완료\n",
                result.after().dateTime().toLocalTime(),
                result.after().status()
        );
        printBlankLine();
    }

    public void printAttendUpdateResult(AttendanceSearchResult result) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", result.nickname());
        List<AttendanceResponse> response = result.recordUntilToday().responses();

        response.forEach(this::printAttendanceRecord);
        System.out.println();

        AttendanceGroupByStatus groupByStatus = result.groupByStatus();

        System.out.printf("""
                        출석: %d회
                        지각: %d회
                        결석: %d회
                        """,
                groupByStatus.attendance(),
                groupByStatus.late(),
                groupByStatus.expulsion()
        );

        System.out.println();
        System.out.printf("%s 대상자입니다.\n", groupByStatus.warning()); // todo : 출력 순서랑 이후 날짜 중재
        printBlankLine();
    }

    public void printWarnedStudents(WarnedStudentResponses response) {
        System.out.println("제적 위험자 조회 결과");
        List<WarnedStudentResponse> warnedStudents = response.responses();
        warnedStudents.forEach(student -> {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                    student.name(),
                    student.attendanceGroupByStatus().expulsion(),
                    student.attendanceGroupByStatus().late(),
                    student.attendanceGroupByStatus().warning()
            );
        });
        printBlankLine();
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
        printBlankLine();
    }

    private void printBlankLine() {
        System.out.println();
    }
}
