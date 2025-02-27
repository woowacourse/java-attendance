package attendance.domain.record;

import attendance.domain.checker.AttendanceType;
import attendance.exception.AttendanceException;
import attendance.exception.ExceptionMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttendanceRecordStorage {

    private final List<AttendanceRecord> records = new ArrayList<>();

    public void add(AttendanceRecord record) {
        validateAlreadyAttendance(record);
        if (record.getAttendanceType() != AttendanceType.ABSENCE) {
            records.add(record);
        }
    }

    public void remove(String nickname, LocalDate arrivalDate) {
        Optional<AttendanceRecord> originRecord = find(nickname, arrivalDate);
        originRecord.ifPresent(records::remove);
    }

    public Optional<AttendanceRecord> find(String nickName, LocalDate arrivalDate) {
        return records.stream()
                .filter(record -> record.isSame(nickName, arrivalDate))
                .findAny();
    }

    public AttendanceRecord findWithAbsenceRecord(String nickname, LocalDate arrivalDate) {
        Optional<AttendanceRecord> record = find(nickname, arrivalDate);
        return record.orElseGet(() -> makeAbsenceRecord(nickname, arrivalDate));
    }

    public int calculateAttendanceCountInMonth(String nickname, LocalDate today) {
        return (int) records.stream()
                .filter(record -> record.checkNickname(nickname))
                .filter(record -> record.checkIsInMonth(today.getYear(), today.getMonth()))
                .filter(record -> record.checkType(AttendanceType.ATTENDANCE))
                .count();
    }

    public int calculateLateCountInMonth(String nickname, LocalDate today) {
        return (int) records.stream()
                .filter(record -> record.checkNickname(nickname))
                .filter(record -> record.checkIsInMonth(today.getYear(), today.getMonth()))
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
}
