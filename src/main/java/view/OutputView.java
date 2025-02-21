package view;

import domain.AttendanceStatus;
import domain.Manage;
import dto.AttendanceRecord;
import dto.AttendanceResult;
import dto.CrewAlmostExpelledResult;
import dto.Formatter;
import dto.ModifiedResult;
import dto.MonthAttendanceRecordsResult;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class OutputView {

    public static void printAttendanceResult(AttendanceResult result) {
        System.out.printf("%s (%s)%n",
                LocalDateTime.of(result.date(), result.time()).format(Formatter.DATETIME_FORMATTER),
                result.status().getTitle()
        );
    }

    public static void printModifiedResult(ModifiedResult modifiedResult) {
        StringBuilder message = new StringBuilder();
        message.append(
                LocalDateTime.of(modifiedResult.date(), modifiedResult.before().time())
                        .format(Formatter.DATETIME_FORMATTER));
        message.append(String.format(" (%s) -> ", modifiedResult.before().status().getTitle()));
        message.append(modifiedResult.after().time().format(Formatter.TIME_FORMATTER));
        message.append(String.format(" (%s)", modifiedResult.after().status().getTitle()));
        message.append(" 수정 완료!%n%n");

        System.out.printf(message.toString());
    }

    public static void printMonthAttendanceRecords(MonthAttendanceRecordsResult monthAttendanceRecordResult) {
        System.out.printf("이번 달 %s의 출석 기록입니다.%n%n", monthAttendanceRecordResult.nickname());
        monthAttendanceRecordResult.history().forEach(innerHistory -> {
            StringBuilder message = new StringBuilder();
            message.append(innerHistory.date().format(Formatter.DATE_FORMATTER));
            message.append(" ");
            message.append(convertToTime(innerHistory));
            message.append(String.format(" (%s)", innerHistory.status().getTitle()));
        });
        System.out.println();
        System.out.printf("출석: %d회%n",
                monthAttendanceRecordResult.attendanceStatusStatistics()
                        .getCountByStatus(AttendanceStatus.ATTENDANCE));
        System.out.printf("지각: %d회%n",
                monthAttendanceRecordResult.attendanceStatusStatistics()
                        .getCountByStatus(AttendanceStatus.LATE));
        System.out.printf("결석: %d회%n",
                monthAttendanceRecordResult.attendanceStatusStatistics()
                        .getCountByStatus(AttendanceStatus.ABSENT_LATE, AttendanceStatus.ABSENT));
        System.out.println();

        if (!monthAttendanceRecordResult.manage().equals(Manage.NONE)) {
            System.out.printf("%s 대상자입니다.%n%n", monthAttendanceRecordResult.manage().getDescription());
        }
    }

    private static String convertToTime(AttendanceRecord attendanceRecord) {
        if (attendanceRecord.status() == AttendanceStatus.ABSENT) {
            return "--:--";
        }
        return attendanceRecord.time()
                .format(Formatter.TIME_FORMATTER);
    }

    public static void printCrewsAlmostExpelled(List<CrewAlmostExpelledResult> result) {
        result = result.stream()
                .sorted(
                        Comparator.comparing(CrewAlmostExpelledResult::calculateTotalAbsentCount,
                                        Comparator.reverseOrder())
                                .thenComparing(CrewAlmostExpelledResult::nickname))
                .toList();

        result.forEach(crew ->
                System.out.printf("- %s: %s %d회, %s %d회 (%s)%n",
                        crew.nickname(),
                        AttendanceStatus.ABSENT_LATE.getTitle(),
                        crew.attendanceStatusStatistics()
                                .getCountByStatus(AttendanceStatus.ABSENT_LATE, AttendanceStatus.ABSENT),
                        AttendanceStatus.LATE.getTitle(),
                        crew.attendanceStatusStatistics()
                                .getCountByStatus(AttendanceStatus.LATE),
                        crew.manage().getDescription()
                )
        );
    }
}
