package view;

import domain.AttendanceDateTime;
import domain.AttendanceStatus;
import domain.Crew;
import domain.Penalty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import vo.DangerCrew;

public class OutputView {

    public static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("MM월 dd일 EEE요일 HH:mm");
    public static final DateTimeFormatter ABSENCE_FORMATTER =
        DateTimeFormatter.ofPattern("MM월 dd일 EEE요일 --:--");

    public static void printException(IllegalArgumentException e) {
        System.out.println("[ERROR] " + e.getMessage());
        blankLine();
    }

    public static void printAttendResult(AttendanceDateTime result) {
        String formatted = formatAttendanceDateTime(result);
        System.out.println(formatted);
        blankLine();
    }

    public static void printModifyResult(AttendanceDateTime before, AttendanceDateTime after) {
        String formattedBefore = formatAttendanceDateTime(before);
        String formattedAfter = formatAttendanceDateTime(after);
        System.out.printf("%s -> %s 수정 완료!%n", formattedBefore, formattedAfter);
        blankLine();
    }

    public static void printAttendanceList(Crew crew, List<AttendanceDateTime> attendanceList) {
        System.out.println("이번 달 " + crew.getName() + "의 출석 기록입니다.");
        blankLine();

        attendanceList.stream()
            .map(OutputView::formatAttendanceDateTime)
            .forEach(System.out::println);
        blankLine();
    }

    private static String formatAttendanceDateTime(AttendanceDateTime attendanceDateTime) {
        AttendanceStatus status = attendanceDateTime.getAttendanceStatus();
        LocalDate date = attendanceDateTime.getDate();
        LocalTime time = attendanceDateTime.getTime();

        if (time == null) {
            return String.format("%s (%s)", ABSENCE_FORMATTER.format(date), status);
        }
        return String.format("%s (%s)",
            DATE_TIME_FORMATTER.format(LocalDateTime.of(date, time)), status);
    }

    private static void blankLine() {
        System.out.println();
    }

    public static void printCountAndPenalty(int onTime, int late, int absence, Penalty penalty) {
        System.out.println("출석: " + onTime);
        System.out.println("지각: " + late);
        System.out.println("결석: " + absence);
        blankLine();

        if (penalty != Penalty.NONE) {
            System.out.println(penalty.description() + "대상자입니다.");
            blankLine();
        }
    }

    public static void printDangerCrews(List<DangerCrew> vos) {
        System.out.println("제적 위험자 조회 결과");
        vos.sort(
            Comparator.comparing(DangerCrew::penalty)
                .thenComparing((vo1, vo2) -> {
                    int left = vo1.absence() + (vo1.late() / 3);
                    int right = vo2.absence() + (vo2.late() / 3);
                    return Integer.compare(left, right);
                })
                .thenComparing(DangerCrew -> DangerCrew.crew().getName())
        );

        for (DangerCrew vo : vos) {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                vo.crew().getName(), vo.absence(), vo.late(), vo.penalty().description());
        }
        blankLine();
    }
}
