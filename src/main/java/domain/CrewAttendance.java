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

    public void addAttendance(WorkDateTime workDateTime) {
        attendance.addDateTime(workDateTime);
    }

    public void updateAttendance(WorkDateTime updateWorkDateTime) {
        attendance.updateDateTime(updateWorkDateTime);
    }

    public WorkDateTime retrieveAttendance(WorkDate workDate) {
        return attendance.retrieveDateTime(workDate);
    }

    public List<WorkDateTime> retrieveAttendanceOrderByDate() {
        return attendance.retrieveDateTimes().stream()
                .sorted()
                .toList();
    }

    public AttendanceStatus calculateAttendanceStatus(WorkDate workDate) {
        return attendance.calculateAttendanceStatus(workDate);
    }

    public Map<AttendanceStatus, Integer> calculateAttendanceStatusCount() {
        return attendance.calculateAttendanceStatusCount();
    }

    public boolean isPenalty() {
        return !calculatePenalty().isNone();
    }

    public Penalty calculatePenalty() {
        return attendance.calculatePenalty();
    }

    public Crew getCrew() {
        return crew;
    }
}
