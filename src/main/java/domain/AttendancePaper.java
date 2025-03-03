package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import util.DateTimeConvertor;

public class AttendancePaper {
    private final Long id;
    private final String crewName;
    private final Map<LocalDate, AttendanceRecord> attendanceRecords;

    public AttendancePaper(final Long id, final String crewName,
                           final Map<LocalDate, AttendanceRecord> attendanceRecords) {
        this.id = id;
        this.crewName = crewName;
        this.attendanceRecords = attendanceRecords;
    }

    public String getCrewName() {
        return crewName;
    }

    public AttendanceRecord addAttendance(final LocalDateTime localDateTime) {
        final AttendanceRecord attendanceRecord = AttendanceRecord.of(localDateTime);
        attendanceRecords.put(localDateTime.toLocalDate(), attendanceRecord);
        return attendanceRecord;
    }

    public void validateExistingAttendanceRecord(final LocalDate localDate) {
        if (attendanceRecords.containsKey(localDate)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %s 출석 기록이 존재합니다. 수정 기능을 이용해주세요",
                            DateTimeConvertor.convertToLocalDateKoreanFormat(
                                    localDate)));
        }
    }

    public AttendanceModification modifyAttendance(final LocalDate localDate, final AttendanceTime attendanceTime) {
        final LocalTime localtime = attendanceTime.getLocalTime();
        final AttendanceRecord beforeAttendanceRecord = getAttendanceRecordByDate(localDate);
        addAttendance(LocalDateTime.of(localDate, localtime));
        final AttendanceRecord afterAttendanceRecord = getAttendanceRecordByDate(localDate);
        return new AttendanceModification(beforeAttendanceRecord, afterAttendanceRecord);
    }

    public void validateExistAttendanceDate(final LocalDate localDate) {
        if (!attendanceRecords.containsKey(localDate)) {
            throw new IllegalArgumentException("[ERROR] 출석 기록이 존재하지 않습니다.");
        }
    }

    public boolean existAttendance(final LocalDate localDate) {
        return attendanceRecords.containsKey(localDate);
    }

    public AttendanceRecord getAttendanceRecordByDate(final LocalDate localDate) {
        return attendanceRecords.get(localDate);
    }

    public List<AttendanceRecord> getAttendanceRecords() {
        return attendanceRecords.values().stream().toList();
    }
}
