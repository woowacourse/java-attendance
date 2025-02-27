package attendance.view;

import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceStatus;
import attendance.dto.CrewHistoryDto;
import attendance.dto.CrewHistoryDto.AttendanceRecordDto;
import attendance.dto.WarningResultDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class OutputView {
    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 EEEE HH:mm");
    private final static DateTimeFormatter NO_ATTENDANCE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 EEEE --:--");

    public void displayAttendanceResult(AttendanceRecord record) {
        System.out.println(getFormattedRecord(record));
        System.out.println(System.lineSeparator());
    }

    public void displayCrewHistory(CrewHistoryDto dto) {
        CustomStringBuilder sb = new CustomStringBuilder();
        sb.appendLine(String.format("이번 달 %s의 출석 기록입니다.", dto.crewName()));

        Map<LocalDate, AttendanceRecordDto> attendanceMap = buildAttendanceMap(dto);
        SortedSet<LocalDate> allDates = buildSortedDates(attendanceMap, dto.noAttendanceDates());

        for (LocalDate date : allDates) {
            sb.appendLine(formatDateLine(date, attendanceMap));
        }

        sb.appendLine();
        sb.appendLine(getStatistics(dto));
        sb.appendLine("");
        sb.appendLine(String.format("%s입니다.", dto.warningStatus()));
        sb.print();
    }

    private Map<LocalDate, AttendanceRecordDto> buildAttendanceMap(CrewHistoryDto dto) {
        return dto.recordDtos().stream()
                .collect(Collectors.toMap(
                        r -> r.attendanceDateTime().toLocalDate(),
                        r -> r
                ));
    }

    private SortedSet<LocalDate> buildSortedDates(Map<LocalDate, AttendanceRecordDto> attendanceMap,
                                                  List<LocalDate> noAttendanceDates) {
        SortedSet<LocalDate> allDates = new TreeSet<>();
        allDates.addAll(attendanceMap.keySet());
        allDates.addAll(noAttendanceDates);
        return allDates;
    }

    private String formatDateLine(LocalDate date, Map<LocalDate, AttendanceRecordDto> attendanceMap) {
        if (attendanceMap.containsKey(date)) {
            AttendanceRecordDto recordDto = attendanceMap.get(date);
            String formattedDateTime = recordDto.attendanceDateTime().format(DATE_FORMATTER);
            return String.format("%s (%s)", formattedDateTime, recordDto.AttendanceStatus());
        }
        return String.format("%s (결석)", date.format(NO_ATTENDANCE_FORMATTER));
    }

    private String getStatistics(CrewHistoryDto dto) {
        return String.format("%s: %d회%n%s: %d회%n%s: %d회",
                AttendanceStatus.PRESENT.getTitle(), dto.presentCount(),
                AttendanceStatus.LATE.getTitle(), dto.lateCount(),
                AttendanceStatus.ABSENT.getTitle(), dto.absentCount());
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

    public void printError(String message) {
        System.out.println(message);
        System.out.println(System.lineSeparator());
    }
}
