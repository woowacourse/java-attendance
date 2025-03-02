package domain;

import except.AttendanceException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import strategy.CurrentDateGenerateStrategy;

public class DateCrewAttendanceManager {

    private static final String ATTENDANCE_DOENST_EXIST = "존재하지 않는 출석 기록입니다.";
    private static final String DUPLICATE_ATTENDANCE = "이미 출석하였습니다. 수정할려면 수정 기능을 참조해주세요";

    private final Map<AttendanceDate, CrewAttendance> dateCrewAttendances;
    private final CurrentDateGenerateStrategy currentDateGenerateStrategy;

    public DateCrewAttendanceManager(CurrentDateGenerateStrategy currentDateGenerateStrategy) {
        this.dateCrewAttendances = new HashMap<>();
        this.currentDateGenerateStrategy = currentDateGenerateStrategy;
    }

    public void addAttendance(LocalTime time) {
        AttendanceDate attendanceDate = new AttendanceDate(currentDateGenerateStrategy.now());
        AttendanceTime attendanceTime = new AttendanceTime(time, attendanceDate);
        CrewAttendance crewAttendance = new CrewAttendance(attendanceTime);
        addAttendance(attendanceDate, crewAttendance);
    }

    private void addAttendance(AttendanceDate attendanceDate, CrewAttendance crewAttendance) {
        if (dateCrewAttendances.containsKey(attendanceDate)) {
            throw new AttendanceException(DUPLICATE_ATTENDANCE);
        }
        dateCrewAttendances.put(attendanceDate, crewAttendance);
    }

    public CrewAttendance crewAttendance(LocalDate date) {
        AttendanceDate attendanceDate = new AttendanceDate(date);
        validtateDateAttendanceExist(attendanceDate);
        return dateCrewAttendances.get(attendanceDate);
    }

    public void modifyAttendance(LocalDate modifyDate, LocalTime modifyTime) {
        AttendanceDate attendanceDate = new AttendanceDate(modifyDate);
        AttendanceTime attendanceTime = new AttendanceTime(modifyTime, attendanceDate);
        validtateDateAttendanceExist(attendanceDate);
        dateCrewAttendances.put(attendanceDate, new CrewAttendance(attendanceTime));
    }

    private void validtateDateAttendanceExist(AttendanceDate attendanceDate) {
        if (!dateCrewAttendances.containsKey(attendanceDate)) {
            throw new AttendanceException(ATTENDANCE_DOENST_EXIST);
        }
    }

    public CrewAttendanceHistories crewAttendancesHistory() {
        LocalDate attendanceHistoryLastDate = currentDateGenerateStrategy.now();
        List<CrewAttendanceHistory> crewAttendanceHistories = dateCrewAttendances.entrySet()
                .stream()
                .filter((entry) -> entry.getKey()
                        .isBefore(attendanceHistoryLastDate))
                .map((dateCrewAttendanceEntry) -> new CrewAttendanceHistory(dateCrewAttendanceEntry.getValue(),
                        dateCrewAttendanceEntry.getKey()))
                .collect(Collectors.toList());
        return CrewAttendanceHistories.from(crewAttendanceHistories);
    }

    public void addAttendance(AttendanceReadUnit attendanceReadUnit) {
        CrewAttendance crewAttendance = new CrewAttendance(attendanceReadUnit.attendanceTime());
        addAttendance(attendanceReadUnit.attendanceDate(), crewAttendance);
    }
}
