package view;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import domain.AttendanceStatus;
import domain.Manage;
import dto.AttendanceHistoryResponseDto;
import dto.AttendanceResponseDto;
import dto.CrewAlmostExpelledResponseDto;
import constant.FormatterConstant;
import dto.HistoryDto;
import dto.ModifiedResponseDto;

public class OutputView {

    private OutputView() {
    }

    public static void printAttendanceResult(AttendanceResponseDto result) {
        String dateTime = LocalDateTime.of(result.date(), result.time()).format(FormatterConstant.DATETIME_FORMATTER);
        System.out.printf(dateTime + " (%s)%n", result.status().getName());
    }

    public static void printModifiedResult(ModifiedResponseDto modifiedResponseDto) {
        StringBuilder message = new StringBuilder();
        message.append(
            LocalDateTime.of(modifiedResponseDto.date(), modifiedResponseDto.before().time())
                .format(FormatterConstant.DATETIME_FORMATTER));
        message.append(String.format(" (%s) -> ", modifiedResponseDto.before().status().getName()));
        message.append(modifiedResponseDto.after().time().format(FormatterConstant.TIME_FORMATTER));
        message.append(String.format(" (%s)", modifiedResponseDto.after().status().getName()));
        message.append(" 수정 완료!%n%n");

        System.out.printf(message.toString());
    }

    public static void printHistory(AttendanceHistoryResponseDto historyResult) {
        System.out.printf("이번 달 %s의 출석 기록입니다.%n%n", historyResult.nickname());
        historyResult.histories().forEach(history -> {
            StringBuilder message = new StringBuilder();
            message.append(history.date().format(FormatterConstant.DATE_FORMATTER));
            message.append(" ");
            message.append(convertToTime(history));
            message.append(String.format(" (%s)", history.status().getName()));
        });
        System.out.println();
        System.out.printf("출석: %d회%n", historyResult.statusCounter().get(AttendanceStatus.ATTENDANCE));
        System.out.printf("지각: %d회%n", historyResult.statusCounter().get(AttendanceStatus.LATE));
        System.out.printf("결석: %d회%n", historyResult.statusCounter().get(AttendanceStatus.ABSENT));
        System.out.println();

        if (historyResult.manage() != Manage.NONE) {
            System.out.printf("%s 대상자입니다.%n%n", historyResult.manage().getName());
        }
    }

    private static String convertToTime(HistoryDto history) {
        if (!history.isChecked() || history.time() == null) {
            return "--:--";
        }
        return history.time().format(FormatterConstant.TIME_FORMATTER);
    }

    public static void printCrewsAlmostExpelled(List<CrewAlmostExpelledResponseDto> result) {
        String format = "- %s: %s %d회, %s %d회 (%s)%n";
        result = result.stream()
            .sorted(
                Comparator.comparing(CrewAlmostExpelledResponseDto::calculateTotalCount, Comparator.reverseOrder())
                    .thenComparing(CrewAlmostExpelledResponseDto::nickname))
            .toList();

        result.forEach(crew ->
            System.out.printf(format,
                crew.nickname(),
                AttendanceStatus.ABSENT.getName(),
                crew.attendanceStatusStatistics().get(AttendanceStatus.ABSENT),
                AttendanceStatus.LATE.getName(),
                crew.attendanceStatusStatistics().get(AttendanceStatus.LATE),
                crew.manage().getName()
            )
        );
    }
}
