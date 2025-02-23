package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Objects;

public class Crew {
    private final String name;
    private final AttendanceHistoryManager attendanceHistoryManager;

    public Crew(String name) {
        validateName(name);
        this.name =  name;
        this.attendanceHistoryManager = new AttendanceHistoryManager();
    }

    public void addAttendanceHistory(AttendanceHistory attendanceHistory) {
        attendanceHistoryManager.addAttendanceHistory(attendanceHistory);
    }

    public AttendanceHistory modifyAttendanceResult(AttendanceHistory attendanceHistory, LocalTime localTime) {
        return attendanceHistoryManager.modifyAttendanceResult(attendanceHistory, localTime);
    }

    public String getName() {
        return name;
    }

    public AttendanceHistory getAttendanceHistory(LocalDate localDate) {
        return attendanceHistoryManager.getAttendanceHistory(localDate);
    }

    public Map<AttendanceType, Integer> calculateAttendanceResult(LocalDate localDate) {
        return attendanceHistoryManager.calculateAttendanceResult(localDate);
    }

    public CrewStatus calculateCrewStatus(Map<AttendanceType, Integer> attendanceResult) {
        return attendanceHistoryManager.calculateCrewStatus(attendanceResult);
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("크루 이름은 공백일 수 없습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Crew crew = (Crew) o;
        return Objects.equals(name, crew.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
