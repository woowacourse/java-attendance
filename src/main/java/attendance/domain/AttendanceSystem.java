package attendance.domain;

import attendance.dto.UpdateResult;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

public class AttendanceSystem {

    private final CrewStorage crewStorage;
    private final AttendanceRecordStorage recordStorage;
    private final HolidayChecker holidayChecker;

    public AttendanceSystem(
            CrewStorage crewStorage,
            AttendanceRecordStorage recordStorage,
            HolidayChecker holidayChecker
    ) {
        this.crewStorage = crewStorage;
        this.recordStorage = recordStorage;
        this.holidayChecker = holidayChecker;
    }

    public AttendanceRecord saveAttendanceRecord(String nickname, LocalDateTime arrivalDateTime) {
        validateCrew(nickname);
        validateHoliday(arrivalDateTime.toLocalDate());

        AttendanceStatusType attendanceType = calculateAttendanceType(arrivalDateTime);
        AttendanceRecord newRecord = new AttendanceRecord(nickname, arrivalDateTime, attendanceType);
        recordStorage.add(newRecord);
        return newRecord;
    }

    public UpdateResult updateAttendanceRecord(String nickname, LocalDate date, LocalTime newTime) {
        validateCrew(nickname);
        validateHoliday(date);

        Optional<AttendanceRecord> oldRecord = recordStorage.find(nickname, date);
        AttendanceRecord newRecord = makeNewRecord(nickname, date, newTime);
        recordStorage.update(newRecord);
        return new UpdateResult(oldRecord.get(), newRecord);
    }

    public List<AttendanceRecord> searchAttendanceRecordsByCrew(String nickname, Month month) {
        validateCrew(nickname);
        return recordStorage.findUnmodifiedRecordsByNickname(nickname, month);
    }

    private void validateCrew(String nickname) {
        boolean isNotContained = !crewStorage.isContained(nickname);
        if (isNotContained) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    private void validateHoliday(LocalDate localDate) {
        boolean isHoliday = holidayChecker.isHoliday(localDate);
        if (isHoliday) {
            throw new IllegalArgumentException("[ERROR] 12월 7일 토요일은 등교일이 아닙니다.");
        }
    }

    private AttendanceStatusType calculateAttendanceType(LocalDateTime dateTime) {
        boolean isMonday = dateTime.getDayOfMonth() == DayOfWeek.MONDAY.getValue();
        return CampusSchedule.checkAttendance(isMonday, dateTime.toLocalTime());
    }

    private AttendanceRecord makeNewRecord(String nickname, LocalDate date, LocalTime newTime) {
        LocalDateTime newDateTime = LocalDateTime.of(date, newTime);
        AttendanceStatusType attendanceType = calculateAttendanceType(newDateTime);
        return new AttendanceRecord(nickname, newDateTime, attendanceType);
    }
}
