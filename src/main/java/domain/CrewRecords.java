package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CrewRecords {
    private final Map<Crew, AttendanceRecords> records;

    public CrewRecords(Map<Crew, AttendanceRecords> records) {
        this.records = records;
    }

    public void addRecord(Crew crew, AttendanceRecord record) {
        validateCrew(crew);
        AttendanceRecords attendanceRecords = records.get(crew);
        if (attendanceRecords.hasRecordOnDate(record.getDate())) {
            throw new IllegalArgumentException("[ERROR] 이미 출석하였습니다. 수정 기능을 이용해 주세요." + System.lineSeparator());
        }
        attendanceRecords.add(record);
    }

    public void updateRecord(Crew crew, LocalDate oldDate, LocalTime newTime) {
        validateCrew(crew);
        records.get(crew).update(oldDate, newTime);
    }

    public AttendanceRecord getRecordOnDate(Crew crew, LocalDate date) {
        validateCrew(crew);
        return records.get(crew).getRecordOnDate(date);
    }

    public AttendanceRecords getAttendanceRecordsOf(Crew crew) {
        validateCrew(crew);
        return records.get(crew);
    }

    public WarningStatus getWarningStatus(Crew crew) {
        AttendanceRecords attendanceRecords = records.get(crew);
        return attendanceRecords.getWarningStatus();
    }

    public List<Crew> getWarnedCrews() {
        Stream<Crew> warnedCrews = records.keySet()
                .stream()
                .filter(crew -> getWarningStatus(crew) != WarningStatus.NONE);
        return sortWarnedCrews(warnedCrews);
    }

    public int getTardyCount(Crew crew) {
        AttendanceRecords attendanceRecords = records.get(crew);
        return attendanceRecords.getAttendanceCount(AttendanceStatus.TARDY);
    }

    public int getAbsentCount(Crew crew) {
        AttendanceRecords attendanceRecords = records.get(crew);
        return attendanceRecords.getAttendanceCount(AttendanceStatus.ABSENT);
    }

    private List<Crew> sortWarnedCrews(Stream<Crew> warnedCrews) {
        return warnedCrews.sorted(Comparator.comparing((Crew crew) -> getConvertedAbsences(crew) * -1)
                        .thenComparing(crew -> getTardiesAfterConversion(crew) * -1)
                        .thenComparing(Crew::name))
                .toList();
    }

    private int getConvertedAbsences(Crew crew) {
        AttendanceRecords attendanceRecords = records.get(crew);
        return attendanceRecords.getConvertedAbsences();
    }

    private int getTardiesAfterConversion(Crew crew) {
        AttendanceRecords attendanceRecords = records.get(crew);
        return attendanceRecords.getTardiesAfterConversion();
    }

    private void validateCrew(Crew crew) {
        if (!records.containsKey(crew)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다." + System.lineSeparator());
        }
    }
}
