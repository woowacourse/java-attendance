package domain;

import java.util.List;
import java.util.Map;

public class CrewAttendance {
    private final Crew crew;
    private final Attendance attendance;

    public CrewAttendance(Crew crew, Attendance attendance) {
        this.crew = crew;
        this.attendance = attendance;
    }

    public void addAttendance(DateTime dateTime) {
        attendance.addDateTime(dateTime);
    }

    public void updateAttendance(DateTime updateDateTime) {
        attendance.updateDateTime(updateDateTime);
    }

    public DateTime retrieveDateTime(Date date) {
        return attendance.retrieveDateTime(date);
    }

    public List<DateTime> retrieveDateTimesOrderByDate() {  // TODO. 정렬 조건을 이용한 추상화
        return attendance.retrieveDateTimes().stream()
                .sorted()
                .toList();
    }

    public AttendanceStatus calculateAttendanceStatus(Date date) {
        return attendance.calculateAttendanceStatus(date);
    }

    public List<AttendanceStatus> calculateAttendanceStatuses() {
        return attendance.calculateAttendanceStatuses();
    }

    public Map<AttendanceStatus, Integer> calculateAttendanceStatusCount() {
        return attendance.calculateAttendanceStatusCount();
    }

    public boolean isPenalty() {
        return !calculatePenalty().equals(Penalty.NONE);
    }

    public Penalty calculatePenalty() {
        return Penalty.calculatePenalty(calculateAttendanceStatuses());
    }

    public Crew getCrew() {
        return crew;
    }
}
