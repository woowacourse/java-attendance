package attendance.domain;

public class DangerousCrew {

    private final Crew crew;
    private final CrewStatus crewStatus;
    private final AttendanceHistories attendanceHistories;

    private DangerousCrew(Crew crew, CrewStatus crewStatus, AttendanceHistories attendanceHistories) {
        this.crew = crew;
        this.crewStatus = crewStatus;
        this.attendanceHistories = attendanceHistories;
    }

    public static DangerousCrew of(Crew crew, CrewStatus crewStatus, AttendanceHistories attendanceHistories) {
        return new DangerousCrew(crew, crewStatus, attendanceHistories);
    }

    public String getCrewName() {
        return crew.getName();
    }

    public String getStatusName() {
        return crewStatus.getStatusDescription();
    }

    public AttendanceHistories getAttendanceHistories() {
        return attendanceHistories;
    }
}
