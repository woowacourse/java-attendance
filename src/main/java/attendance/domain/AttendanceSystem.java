package attendance.domain;

import attendance.domain.checker.AttendanceChecker;
import attendance.domain.checker.AttendanceType;
import attendance.domain.crew.CrewStorage;
import attendance.domain.record.AttendanceRecord;
import attendance.exception.ExceptionMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttendanceSystem {

    private final CrewStorage crewStorage;
    private final AttendanceChecker attendanceChecker;
    private final List<AttendanceRecord> records = new ArrayList<>();

    public AttendanceSystem(CrewStorage crewStorage, AttendanceChecker attendanceChecker) {
        this.crewStorage = crewStorage;
        this.attendanceChecker = attendanceChecker;
    }

    public void addAttendanceRecord(String crewNickname, LocalDateTime arrivalDateTime) {
        crewStorage.validateIsNotContained(crewNickname);
        validateAlreadyAttendance(crewNickname, arrivalDateTime.toLocalDate());
        AttendanceType attendanceType = attendanceChecker.checkAttendance(arrivalDateTime);
        AttendanceRecord newRecord = new AttendanceRecord(crewNickname, arrivalDateTime, attendanceType);
        records.add(newRecord);
    }

    public Optional<AttendanceRecord> findAttendanceRecord(String crewNickname, LocalDate date) {
        return records.stream()
                .filter(record -> record.isSame(crewNickname, date))
                .findAny();
    }

    public void updateAttendance(String validCrewNickname, LocalDate localDate, LocalTime newArrivalTime) {

    }

    private void validateAlreadyAttendance(String crewNickname, LocalDate date) {
        Optional<AttendanceRecord> originRecord = findAttendanceRecord(crewNickname, date);
        if (originRecord.isPresent()) {
            throw new IllegalArgumentException(ExceptionMessage.ALREADY_ATTENDANCE.getMessage());
        }
    }
}
