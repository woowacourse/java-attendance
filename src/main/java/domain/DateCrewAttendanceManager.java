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
        this.currentDateGenerateStrategy = currentDateGenerateStrategy;
        this.dateCrewAttendances = new HashMap<>();
    }

    public void addAttendance(LocalTime time) {
        AttendanceDate attendanceDate = new AttendanceDate(currentDateGenerateStrategy.now());
        AttendanceTime attendanceTime = new AttendanceTime(time, attendanceDate);
        if (dateCrewAttendances.containsKey(attendanceDate)) {
            throw new AttendanceException(DUPLICATE_ATTENDANCE);
        }
        dateCrewAttendances.put(attendanceDate, new CrewAttendance(attendanceTime));
    }

    public CrewAttendance crewAttendance(LocalDate date) {
        AttendanceDate attendanceDate = new AttendanceDate(date);
        return dateCrewAttendances.get(attendanceDate);
    }

    public void modifyAttendance(LocalDate modifyDate, LocalTime modifyTime) {
        AttendanceDate attendanceDate = new AttendanceDate(modifyDate);
        AttendanceTime attendanceTime = new AttendanceTime(modifyTime, attendanceDate);
        if (!dateCrewAttendances.containsKey(attendanceDate)) {
            throw new AttendanceException(ATTENDANCE_DOENST_EXIST);
        }
        dateCrewAttendances.put(attendanceDate, new CrewAttendance(attendanceTime));
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
}
