package attendance.domain;

import attendance.domain.checker.AttendanceChecker;
import attendance.domain.checker.AttendanceType;
import attendance.domain.crew.CrewStorage;
import attendance.domain.record.AttendanceRecord;
import attendance.domain.record.AttendanceRecordStorage;
import attendance.domain.risk.RiskType;
import attendance.dto.AttendanceState;
import attendance.dto.RecordUpdateResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

public class AttendanceSystem {

    private final CrewStorage crewStorage;
    private final AttendanceChecker attendanceChecker;
    private final AttendanceRecordStorage recordStorage;

    public AttendanceSystem(
            CrewStorage crewStorage,
            AttendanceChecker attendanceChecker,
            AttendanceRecordStorage recordStorage
    ) {
        this.crewStorage = crewStorage;
        this.attendanceChecker = attendanceChecker;
        this.recordStorage = recordStorage;
    }

    public AttendanceRecord addAttendanceRecord(String nickname, LocalDateTime arrivalDateTime) {
        crewStorage.validateIsNotContained(nickname);
        AttendanceRecord newRecord = makeNewAttendance(nickname, arrivalDateTime);
        recordStorage.add(newRecord);
        return newRecord;
    }

    public RecordUpdateResult updateAttendance(String nickname, LocalDate arrivalDate, LocalTime newTime) {
        crewStorage.validateIsNotContained(nickname);

        AttendanceRecord oldRecord = recordStorage.findWithAbsenceRecord(nickname, arrivalDate);
        recordStorage.remove(nickname, arrivalDate);

        AttendanceRecord newRecord = makeNewAttendance(nickname, LocalDateTime.of(arrivalDate, newTime));
        recordStorage.add(newRecord);
        return new RecordUpdateResult(oldRecord, newRecord);
    }

    public List<AttendanceRecord> findRecordsInMonth(String nickname, LocalDate today) {
        List<LocalDate> notHolidays = calculateNotHoliday(today.getYear(), today.getMonth());
        return notHolidays.stream()
                .filter(notHoliday -> !notHoliday.isAfter(today))
                .map(notHoliday -> recordStorage.findWithAbsenceRecord(nickname, notHoliday))
                .toList();
    }

    public AttendanceState calculateAttendanceStateInMonth(String nickname, LocalDate today) {
        crewStorage.validateIsNotContained(nickname);
        int maxAttendanceCount = calculateMaxAttendanceCountUntilTodayInMonth(today);
        int attendanceCount = recordStorage.calculateAttendanceCountInMonth(nickname, today);
        int lateCount = recordStorage.calculateLateCountInMonth(nickname, today);
        int absenceCount = maxAttendanceCount - attendanceCount - lateCount;
        return new AttendanceState(nickname, attendanceCount, lateCount, absenceCount);
    }

    public List<AttendanceState> findRiskCrew(LocalDate today) {
        List<String> allNicknames = crewStorage.findAllNicknames();
        return allNicknames.stream()
                .map(nickname -> calculateAttendanceStateInMonth(nickname, today))
                .filter(state -> state.getRiskTyp() != RiskType.NONE)
                .sorted()
                .toList();
    }

    public boolean checkRegisteredCrew(String nickname) {
        return !crewStorage.checkIsNotContained(nickname);
    }

    private AttendanceRecord makeNewAttendance(String nickname, LocalDateTime arrivalDateTime) {
        AttendanceType attendanceType = attendanceChecker.checkAttendance(arrivalDateTime);
        return new AttendanceRecord(nickname, arrivalDateTime, attendanceType);
    }

    private int calculateMaxAttendanceCountUntilTodayInMonth(LocalDate today) {
        List<LocalDate> notHolidays = calculateNotHoliday(today.getYear(), today.getMonth());
        return (int) notHolidays.stream().filter(notHoliday -> !notHoliday.isAfter(today)).count();
    }

    private List<LocalDate> calculateNotHoliday(int year, Month month) {
        return attendanceChecker.calculateNotHolidayInMonth(year, month);
    }
}
