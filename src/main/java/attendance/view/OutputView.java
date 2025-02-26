package attendance.view;

import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceStatus;
import attendance.domain.Crew;
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
}
