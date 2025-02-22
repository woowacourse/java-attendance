package view;

import domain.AttendanceRecord;
import domain.AttendanceStatus;
import domain.AttendanceStatusStatistics;
import domain.Manage;
import dto.AttendanceResult;
import dto.CrewAlmostExpelledResult;
import dto.ModifiedResult;
import dto.MonthRecord;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import util.Formatter;

public class OutputView {

    public static void printAttendanceResult(AttendanceResult result) {
        System.out.printf("%s (%s)%n",
                LocalDateTime.of(result.date(), result.time()).format(Formatter.DATETIME_FORMATTER),
                result.status().getTitle()
        );
    }

    public static void printModifiedResult(ModifiedResult modifiedResult) {
        StringBuilder message = new StringBuilder();
        message.append(modifiedResult.date().format(Formatter.DATE_FORMATTER));
        message.append(" ");
        message.append(modifiedResult.before().time());
        message.append(String.format(" (%s) -> ", modifiedResult.before().status().getTitle()));
        message.append(modifiedResult.after().time());
        message.append(String.format(" (%s)", modifiedResult.after().status().getTitle()));
        message.append(" 수정 완료!%n%n");
        System.out.printf(message.toString());
    }

    public static void printMonthRecord(MonthRecord monthAttendanceRecordResult) {
        System.out.printf("이번 달 %s의 출석 기록입니다.%n%n", monthAttendanceRecordResult.nickname());
        printMonthAttendanceRecordsList(monthAttendanceRecordResult.attendanceRecords());

        printMonthAttendanceRecordStatistics(monthAttendanceRecordResult.attendanceStatusStatistics());

        printManage(monthAttendanceRecordResult.manage());
    }

    private static void printManage(Manage manage) {
        if (manage != Manage.NONE) {
            System.out.printf("%s 대상자입니다.%n%n", manage.getDescription());
        }
    }

    private static void printMonthAttendanceRecordStatistics(AttendanceStatusStatistics attendanceStatusStatistics) {
        System.out.printf("출석: %d회%n",
                attendanceStatusStatistics
                        .getCountByStatus(AttendanceStatus.ATTENDANCE));
        System.out.printf("지각: %d회%n", attendanceStatusStatistics
                .getCountByStatus(AttendanceStatus.LATE));
        System.out.printf("결석: %d회%n%n",
                attendanceStatusStatistics.getCountByStatus(AttendanceStatus.ABSENT_LATE, AttendanceStatus.ABSENT));
    }

    private static void printMonthAttendanceRecordsList(List<AttendanceRecord> monthAttendanceRecordResult) {
        StringBuilder message = new StringBuilder();
        monthAttendanceRecordResult.forEach(attendanceRecord -> {
            message.append(attendanceRecord.date().format(Formatter.DATE_FORMATTER));
            message.append(" ");
            message.append(convertToTime(attendanceRecord));
            message.append(String.format(" (%s)%n", attendanceRecord.attendanceTime().status().getTitle()));
        });
        System.out.println(message);
    }

    private static String convertToTime(AttendanceRecord attendanceRecord) {
        if (attendanceRecord.attendanceTime().status() == AttendanceStatus.ABSENT
                || attendanceRecord.attendanceTime().status() == AttendanceStatus.OFF_DAY) {
            return "--:--";
        }
        return attendanceRecord.attendanceTime().time()
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
