package attendance.domain.record;

import attendance.domain.checker.AttendanceType;
import attendance.exception.AttendanceException;
import attendance.exception.ExceptionMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AttendanceRecordStorage {

    private final Map<AttendanceRecordIdentifier, AttendanceRecord> records = new HashMap<>();

    public void add(AttendanceRecord record) {
        validateAlreadyAttendance(record);
        if (record.getAttendanceType() != AttendanceType.ABSENCE) {
            records.put(new AttendanceRecordIdentifier(record), record);
        }
    }

    public void remove(String nickname, LocalDate arrivalDate) {
        AttendanceRecordIdentifier identifier = new AttendanceRecordIdentifier(nickname, arrivalDate);
        records.remove(identifier);
    }

    public Optional<AttendanceRecord> find(String nickName, LocalDate arrivalDate) {
        AttendanceRecordIdentifier identifier = new AttendanceRecordIdentifier(nickName, arrivalDate);
        return Optional.ofNullable(records.get(identifier));
    }

    public AttendanceRecord findWithAbsenceRecord(String nickname, LocalDate arrivalDate) {
        Optional<AttendanceRecord> record = find(nickname, arrivalDate);
        return record.orElseGet(() -> makeAbsenceRecord(nickname, arrivalDate));
    }

    public int calculateAttendanceCountInMonth(String nickname, LocalDate today) {
        List<AttendanceRecordIdentifier> identifiersInMonth = findIdentifierInMonth(
                nickname, today.getYear(), today.getMonth());
        return (int) identifiersInMonth.stream()
                .map(records::get)
                .filter(record -> record.checkType(AttendanceType.ATTENDANCE))
                .count();
    }

    public int calculateLateCountInMonth(String nickname, LocalDate today) {
        List<AttendanceRecordIdentifier> identifiersInMonth = findIdentifierInMonth(
                nickname, today.getYear(), today.getMonth());
        return (int) identifiersInMonth.stream()
                .map(records::get)
                .filter(record -> record.checkType(AttendanceType.LATE))
                .count();
    }

    private void validateAlreadyAttendance(AttendanceRecord record) {
        Optional<AttendanceRecord> originRecord = find(
                record.getNickname(), record.getArrivalDateTime().toLocalDate());
        if (originRecord.isPresent()) {
            throw new AttendanceException(ExceptionMessage.ALREADY_ATTENDANCE.getMessage());
        }
    }

    private AttendanceRecord makeAbsenceRecord(String nickname, LocalDate arrivalDate) {
        LocalDateTime dateTime = LocalDateTime.of(arrivalDate, LocalTime.MIN);
        return new AttendanceRecord(nickname, dateTime, AttendanceType.ABSENCE);
    }

    private List<AttendanceRecordIdentifier> findIdentifierInMonth(String nickname, int year, Month month) {
        Set<AttendanceRecordIdentifier> allIdentifiers = records.keySet();
        return allIdentifiers.stream()
                .filter(record -> record.checkNickname(nickname))
                .filter(record -> record.checkIsInMonth(year, month))
                .toList();
    }
}
