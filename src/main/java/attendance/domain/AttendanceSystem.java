package attendance.domain;

import attendance.dto.UpdateResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        crewStorage.validateCrew(nickname);
        holidayChecker.validateHoliday(arrivalDateTime.toLocalDate());

        AttendanceType attendanceType = CampusSchedule.calculateAttendanceType(arrivalDateTime);
        AttendanceRecord newRecord = new AttendanceRecord(nickname, arrivalDateTime, attendanceType);
        recordStorage.add(newRecord);
        return newRecord;
    }

    public UpdateResult updateAttendanceRecord(String nickname, LocalDate date, LocalTime newTime) {
        crewStorage.validateCrew(nickname);
        holidayChecker.validateHoliday(date);

        AttendanceRecord oldRecord = recordStorage.find(nickname, date)
                .orElse(makeExpulsionRecord(nickname, date));
        AttendanceRecord newRecord = makeNewRecord(nickname, date, newTime);
        recordStorage.update(newRecord);
        return new UpdateResult(oldRecord, newRecord);
    }

    public List<AttendanceRecord> searchAttendanceRecordsByCrew(String nickname, int year, Month month) {
        crewStorage.validateCrew(nickname);

        int lastDay = LocalDate.of(year, month.getValue(), 1).lengthOfMonth();
        return IntStream.range(1, lastDay + 1)
                .mapToObj(day -> LocalDate.of(year, month.getValue(), day))
                .filter(date -> !holidayChecker.isHoliday(date))
                .map(date -> findRecord(nickname, date))
                .collect(Collectors.toList());
    }

    public RiskStatistic searchRiskStatistic(String nickname, LocalDate startDate, LocalDate endDate) {
        crewStorage.validateCrew(nickname);
        return calculateRiskStatisticsByCrew(nickname, startDate, endDate);
    }

    public List<RiskStatistic> searchRiskStatistics(LocalDate startDate, LocalDate endDate) {
        List<Crew> allCrew = crewStorage.findAll();
        return allCrew.stream()
                .map(crew -> calculateRiskStatisticsByCrew(crew.getName(), startDate, endDate))
                .filter(statistic -> statistic.getRiskType() != RiskType.NONE)
                .toList();
    }

    private RiskStatistic calculateRiskStatisticsByCrew(
            String nickName, LocalDate startDate, LocalDate endDate
    ) {
        int notHolidayCount = holidayChecker.calculateNotHolidayCount(startDate, endDate);
        int attendanceCount = recordStorage.calculateAttendanceCount(nickName, startDate, endDate);
        int lateCount = recordStorage.calculateLateCount(nickName, startDate, endDate);
        int expulsionCount = notHolidayCount - attendanceCount - lateCount;
        return new RiskStatistic(nickName, attendanceCount, expulsionCount, lateCount);
    }

    private AttendanceRecord makeNewRecord(String nickname, LocalDate date, LocalTime newTime) {
        LocalDateTime newDateTime = LocalDateTime.of(date, newTime);
        AttendanceType attendanceType = CampusSchedule.calculateAttendanceType(newDateTime);
        return new AttendanceRecord(nickname, newDateTime, attendanceType);
    }

    private AttendanceRecord findRecord(String nickname, LocalDate date) {
        Optional<AttendanceRecord> record = recordStorage.find(nickname, date);
        return record.orElseGet(() -> makeExpulsionRecord(nickname, date));
    }

    private AttendanceRecord makeExpulsionRecord(String nickname, LocalDate date) {
        return new AttendanceRecord(nickname, LocalDateTime.of(date, LocalTime.MIN), AttendanceType.EXPULSION);
    }
}
