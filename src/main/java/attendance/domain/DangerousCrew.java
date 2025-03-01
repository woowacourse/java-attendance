package attendance.domain;

public class DangerousCrew {

    private final String nickname;
    private final CrewStatus crewStatus;
    private final AttendanceResult attendanceResult;

    private DangerousCrew(String nickname, CrewStatus crewStatus, AttendanceResult attendanceResult) {
        this.nickname = nickname;
        this.crewStatus = crewStatus;
        this.attendanceResult = attendanceResult;
    }

    public static DangerousCrew of(String nickname, CrewStatus crewStatus, AttendanceResult attendanceResult) {
        return new DangerousCrew(nickname, crewStatus, attendanceResult);
    }

    public String getNickname() {
        return nickname;
    }

    public CrewStatus getCrewStatus() {
        return crewStatus;
    }

    public AttendanceResult getAttendanceResult() {
        return attendanceResult;
    }
}
