package attendance.view;

import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceReport;
import attendance.domain.AttendanceStatus;
import attendance.domain.Crew;
import attendance.dto.WarningResultDto;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class OutputView {
    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 EEEE HH:mm");

    public void displayAttendanceResult(AttendanceRecord record) {
        System.out.println(getFormattedRecord(record));
    }

    public void printError(String message) {
        System.out.println(message);
        System.out.println(System.lineSeparator());
    }

    public void displayCrewHistory(Crew crew, AttendanceReport report) {
        CustomStringBuilder sb = new CustomStringBuilder();
        sb.appendLine(String.format("이번 달 %s의 출석 기록입니다.", crew.getName()));

        report.getRecords().stream()
                .sorted(Comparator.comparing(AttendanceRecord::getDate))
                .forEach(record -> sb.appendLine(getFormattedRecord(record)));

        sb.appendLine(String.format("%s: %d회", AttendanceStatus.PRESENT.getTitle(), report.countPresent()));
        sb.appendLine(String.format("%s: %d회", AttendanceStatus.LATE.getTitle(), report.countLate()));
        sb.appendLine(String.format("%s: %d회", AttendanceStatus.ABSENT.getTitle(), report.countAbsent()));

        sb.appendLine();
        sb.appendLine(String.format("%s입니다.", report.getWarningStatus().getTitle()));
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
                record.getDateTIme().format(DATE_FORMATTER),
                record.getAttendanceStatus().getTitle()
        );
    }

    public void displayWarningCrews(List<WarningResultDto> dtos) {
        CustomStringBuilder sb = new CustomStringBuilder();

        dtos.forEach(dto -> {
            sb.appendLine(String.format("- %s: 결석: %d회, 지각: %d회 (%s)",
                    dto.crewName(),
                    dto.absentCount(),
                    dto.lateCount(),
                    dto.status()
            ));
        });

        sb.print();
    }
}
