package attendance.view;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceStatus;
import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.domain.WarningStatus;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;

public class OutputView {
    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 EEEE HH:mm");

    public void displayAttendanceResult(AttendanceRecord record) {
        System.out.println(getFormattedRecord(record));
    }

    public void printError(String message) {
        System.out.println(message);
    }

    public void displayCrewHistory(Crew crew, AttendanceHistory history) {
        CustomStringBuilder sb = new CustomStringBuilder();
        sb.appendLine(String.format("이번 달 %s의 출석 기록입니다.", crew.getName()));

        history.getRecords().stream()
                .sorted(Comparator.comparing(record -> record.getAttendanceDateTime().toLocalDate()))
                .forEach(record -> sb.appendLine(getFormattedRecord(record)));

        Arrays.stream(AttendanceStatus.values())
                .forEach(status -> sb.appendLine(
                        String.format("%s: %d회", status.getTitle(), history.countByAttendanceStatus(status))
                ));

        sb.print();
    }

    public void displayModifyResult(AttendanceRecord oldRecord, AttendanceRecord newRecord) {
        System.out.printf("%s -> %s 수정 완료!%n",
                getFormattedRecord(oldRecord),
                getFormattedRecord(newRecord)
        );
    }

    private String getFormattedRecord(AttendanceRecord record) {
        return String.format("%s (%s)",
                record.getAttendanceDateTime().format(DATE_FORMATTER),
                record.getAttendanceStatus().getTitle()
        );
    }

    public void displayWarning(Crews crews, AttendanceBook attendanceBook) {
        CustomStringBuilder sb = new CustomStringBuilder();

        crews.getAllCrews().values().stream()
                .filter(crew -> attendanceBook.getWarningByCrew(crew.getName()) != WarningStatus.NONE)
                .forEach(crew -> {
                    AttendanceHistory history = attendanceBook.getHistoryByName(crew.getName());
                    long lateCount = history.countByAttendanceStatus(AttendanceStatus.LATE);
                    long absentCount = history.countByAttendanceStatus(AttendanceStatus.ABSENT);
                    sb.appendLine(String.format("- %s: 결석: %d회, 지각: %d회 (%s)",
                            crew.getName(),
                            absentCount,
                            lateCount,
                            history.getWarningStatus().getTitle()
                    ));
                });

        sb.print();
    }
}
