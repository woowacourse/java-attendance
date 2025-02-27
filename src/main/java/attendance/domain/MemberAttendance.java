package attendance.domain;

public class MemberAttendance {
    private final Crew crew;
    private final AttendanceLog attendanceLog;


    public MemberAttendance(Crew crew, AttendanceLog attendances) {
        this.crew = crew;
        this.attendanceLog = attendances;
    }

    public Crew getCrew() {
        return crew;
    }

    public AttendanceLog getAttendances() {
        return attendanceLog;
    }


    public String checkSubjectStatus(int attendanceCount, int lateCount, int absentCount) {
        absentCount += lateCount / 3;
        if (absentCount > 5) {
            return "제적 대상자";
        }
        if (absentCount >= 3) {
            return "면담 대상자";
        }
        if (absentCount >= 2) {
            return "경고 대상자";
        }
        return null;
    }

}
