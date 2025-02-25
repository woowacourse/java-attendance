package attendance.domain;

import attendance.domain.checker.AttendanceChecker;
import attendance.domain.checker.AttendanceType;
import attendance.domain.crew.CrewStorage;
import attendance.domain.dto.AttendanceState;
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

    public void updateAttendance(String nickname, LocalDate arrivalDate, LocalTime newTime) {
        crewStorage.validateIsNotContained(nickname);
        Optional<AttendanceRecord> originRecord = findAttendanceRecord(nickname, arrivalDate);
        originRecord.ifPresent(records::remove);

        LocalDateTime newDateTime = LocalDateTime.of(arrivalDate, newTime);
        AttendanceType attendanceType = attendanceChecker.checkAttendance(newDateTime);
        AttendanceRecord newRecord = new AttendanceRecord(nickname, newDateTime, attendanceType);
        records.add(newRecord);
    }

    public List<AttendanceRecord> findRecordsInMonth(String nickname, LocalDate today) {
        List<LocalDate> notHolidays = attendanceChecker
                .calculateNotHolidayInMonth(today.getYear(), today.getMonth());
        return notHolidays.stream()
                .filter(notHoliday -> !notHoliday.isAfter(today))
                .map(notHoliday -> findOrElseAbsenceRecord(nickname, notHoliday))
                .toList();
    }

    public AttendanceState calculateAttendanceStateInMonth(String nickname, LocalDate today) {
        crewStorage.validateIsNotContained(nickname);
        List<LocalDate> notHolidays = attendanceChecker
                .calculateNotHolidayInMonth(today.getYear(), today.getMonth());
        int maxAttendanceCount = (int) notHolidays.stream().filter(notHoliday -> !notHoliday.isAfter(today)).count();
        int attendanceCount = calculateAttendanceRecordInMonth(nickname, today);
        int lateCount = calculateLateRecordInMonth(nickname, today);
        return new AttendanceState(nickname, attendanceCount, lateCount,
                maxAttendanceCount - attendanceCount - lateCount);
    }

    public List<AttendanceState> findRiskCrew(LocalDate today) {
        List<String> allNicknames = crewStorage.findAllNicknames();
        return allNicknames.stream()
                .map(nickname -> calculateAttendanceStateInMonth(nickname, today))
                .filter(state -> state.getRiskTyp() != RiskType.NONE)
                .sorted()
                .toList();
    }

    private void validateAlreadyAttendance(String crewNickname, LocalDate date) {
        Optional<AttendanceRecord> originRecord = findAttendanceRecord(crewNickname, date);
        if (originRecord.isPresent()) {
            throw new IllegalArgumentException(ExceptionMessage.ALREADY_ATTENDANCE.getMessage());
        }
    }

    private AttendanceRecord findOrElseAbsenceRecord(String nickname, LocalDate date) {
        Optional<AttendanceRecord> record = findAttendanceRecord(nickname, date);
        return record.orElseGet(() -> AttendanceRecord.makeAbsenceRecord(nickname, date));
    }

    private int calculateAttendanceRecordInMonth(String nickname, LocalDate today) {
        return (int) records.stream()
                .filter(record -> record.checkNickname(nickname))
                .filter(record -> record.checkIsInMonth(today.getYear(), today.getMonth()))
                .filter(record -> record.checkType(AttendanceType.ATTENDANCE))
                .count();
    }

    private int calculateLateRecordInMonth(String nickname, LocalDate today) {
        return (int) records.stream()
                .filter(record -> record.checkNickname(nickname))
                .filter(record -> record.checkIsInMonth(today.getYear(), today.getMonth()))
                .filter(record -> record.checkType(AttendanceType.LATE))
                .count();
    }
}
