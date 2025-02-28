package attendance.domain;

import java.util.Map;

public class MemberAttendanceRecord {
    private final Map<Crew, AttendanceLog> attendanceRecord;

    public MemberAttendanceRecord(Map<Crew, AttendanceLog> attendanceRecord) {
        this.attendanceRecord = attendanceRecord;
    }

    public AttendanceLog findAttendanceLogByCrew(Crew crew){
        return attendanceRecord.get(crew);
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
