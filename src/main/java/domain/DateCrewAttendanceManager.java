package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import strategy.CurrentDateGenerateStrategy;

public class DateCrewAttendanceManager {

    private final Map<AttendanceDate, CrewAttendance> dateCrewAttendances;
    private final CurrentDateGenerateStrategy currentDateGenerateStrategy;

    public DateCrewAttendanceManager(CurrentDateGenerateStrategy currentDateGenerateStrategy) {
        this.currentDateGenerateStrategy = currentDateGenerateStrategy;
        this.dateCrewAttendances = new HashMap<>();
    }

    public void addAttendance(LocalTime time) {
        AttendanceDate attendanceDate = new AttendanceDate(currentDateGenerateStrategy.now());
        AttendanceTime attendanceTime = new AttendanceTime(time, attendanceDate);
        dateCrewAttendances.put(attendanceDate, new CrewAttendance(attendanceTime));
    }

    public CrewAttendance crewAttendance(LocalDate date) {
        AttendanceDate attendanceDate = new AttendanceDate(date);
        return dateCrewAttendances.get(attendanceDate);
    }

    public void modifyAttendance(LocalDate modifyDate, LocalTime modifyTime) {
        AttendanceDate attendanceDate = new AttendanceDate(modifyDate);
        AttendanceTime attendanceTime = new AttendanceTime(modifyTime, attendanceDate);
        dateCrewAttendances.put(attendanceDate, new CrewAttendance(attendanceTime));
    }
}
