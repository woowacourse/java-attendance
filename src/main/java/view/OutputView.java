package view;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import domain.AttendanceStatus;
import domain.Manage;
import dto.AttendanceHistoryResult;
import dto.AttendanceResult;
import dto.CrewAlmostExpelledResult;
import dto.Formatter;
import dto.ModifiedResult;

public class OutputView {

    public static void printAttendanceResult(AttendanceResult result) {
        System.out.println(LocalDateTime.of(result.date(), result.time()).format(Formatter.DATETIME_FORMATTER));
    }

    public static void printModifiedResult(ModifiedResult modifiedResult) {
        StringBuilder message = new StringBuilder();
        message.append(
            LocalDateTime.of(modifiedResult.date(), modifiedResult.before().time())
                .format(Formatter.DATETIME_FORMATTER));
        message.append(String.format(" (%s) -> ", modifiedResult.before().status().getDescription()));
        message.append(modifiedResult.after().time().format(Formatter.TIME_FORMATTER));
        message.append(String.format(" (%s)", modifiedResult.after().status().getDescription()));
        message.append(" 수정 완료!%n%n");

        System.out.printf(message.toString());
    }

    public static void printHistory(AttendanceHistoryResult historyResult) {
        System.out.printf("이번 달 %s의 출석 기록입니다.%n%n", historyResult.nickname());
        historyResult.history().forEach(innerHistory -> {
            StringBuilder message = new StringBuilder();
            message.append(innerHistory.date().format(Formatter.DATE_FORMATTER));
            message.append(" ");
            message.append(convertToTime(innerHistory));
            message.append(String.format(" (%s)", innerHistory.status().getDescription()));
        });
        System.out.println();
        System.out.printf("출석: %d회%n", historyResult.statusCounter().get(AttendanceStatus.ATTENDANCE));
        System.out.printf("지각: %d회%n", historyResult.statusCounter().get(AttendanceStatus.LATE));
        System.out.printf("결석: %d회%n", historyResult.statusCounter().get(AttendanceStatus.ABSENT));
        System.out.println();

        if (!historyResult.manage().equals(Manage.NONE)) {
            System.out.printf("%s 대상자입니다.%n%n", historyResult.manage().getDescription());
        }
    }

    private static String convertToTime(AttendanceHistoryResult.InnerHistory innerHistory) {
        if (innerHistory.time() == null) {
            return "--:--";
        }
        return innerHistory.time().format(Formatter.TIME_FORMATTER);
    }

    public static void printCrewsAlmostExpelled(List<CrewAlmostExpelledResult> result) {
        String format = "- %s: %s %d회, %s %d회 (%s)%n";
        result = result.stream()
            .sorted(
                Comparator.comparing(CrewAlmostExpelledResult::calculateTotalCount, Comparator.reverseOrder())
                    .thenComparing(CrewAlmostExpelledResult::nickname))
            .toList();

        result.forEach(crew ->
            System.out.printf(format,
                crew.nickname(),
                AttendanceStatus.ABSENT.getDescription(),
                crew.attendanceStatusStatistics().get(AttendanceStatus.ABSENT),
                AttendanceStatus.LATE.getDescription(),
                crew.attendanceStatusStatistics().get(AttendanceStatus.LATE),
                crew.manage().getDescription()
            )
        );
    }
}
