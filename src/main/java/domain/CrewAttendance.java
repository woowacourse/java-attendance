package domain;

import java.util.List;

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

    public List<WorkDateTime> retrieveAttendanceOrderByDate() { // TODO. 정렬 조건으로 다양한 요청에 대응
        return attendance.retrieveDateTimes().stream()
                .sorted()
                .toList();
    }

    public Crew getCrew() {
        return crew;
    }
}
