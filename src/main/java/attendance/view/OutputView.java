package attendance.view;

import attendance.domain.Attendance;
import attendance.dto.response.WarnedStudent;
import attendance.dto.response.WarnedStudents;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputView { // todo : 상수 분리 적용 필요, response 파라미터 네이밍 통일, 메서드 순서 정렬

    private static final String NEW_LINE = System.lineSeparator();
    private static final String OUTPUT_MENU = NEW_LINE + NEW_LINE + """
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

    public void printAttendanceRecord(Attendance attendance) {
        LocalDateTime dateTime = attendance.getDateTime();

        String timeContent = dateTime.toLocalTime().toString(); // todo : 파싱 메서드 분리 고려
        if (dateTime.toLocalTime().equals(LocalTime.MIN)) {
            timeContent = "--:--";
        }

        System.out.printf(NEW_LINE + "%d월 %d일 %s %s (%s)",
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                timeContent,
                attendance.getStatus().getName()
        );
    }

    public void printAttendUpdateResult(List<Attendance> attendances) {
        Attendance oldAttendance = attendances.getFirst();
        Attendance updateAttendance = attendances.getLast();

        printAttendanceRecord(oldAttendance);
        System.out.printf(" -> %s (%s) 수정 완료!",
                updateAttendance.getDateTime().toLocalTime(),
                updateAttendance.getStatus().getName()
        );
    }

    public void printAttendanceSearch(List<Attendance> attendances, String nickname) {
        System.out.printf(NEW_LINE + "이번 달 %s의 출석 기록입니다." + NEW_LINE, nickname);

        attendances.forEach(this::printAttendanceRecord);
        System.out.println();

//        System.out.printf(NEW_LINE + """
//                        출석: %d회
//                        지각: %d회
//                        결석: %d회
//                        """,
//                groupByStatus.attendance(),
//                groupByStatus.late(),
//                groupByStatus.expulsion()
//        );
//
//        System.out.println();
//        System.out.printf("%s 대상자입니다.", groupByStatus.warning()); // todo : 출력 순서랑 이후 날짜 중재
    }

    public void printWarnedStudents(WarnedStudents response) {
        System.out.print(NEW_LINE + "제적 위험자 조회 결과");
        List<WarnedStudent> warnedStudents = response.students();
        warnedStudents.forEach(student -> {
            System.out.printf("\n- %s: 결석 %d회, 지각 %d회 (%s)",
                    student.name(),
                    student.groupByStatus().expulsion(),
                    student.groupByStatus().late(),
                    student.groupByStatus().warning()
            );
        });
    }

    public void printErrorMessage(String message) {
        System.out.println(System.lineSeparator() + message);
    }
}
