package attendance.domain;

import java.util.Map;

public class CrewAttendanceRecord {
    private final Map<Crew, AttendanceLog> attendanceRecord;

    public CrewAttendanceRecord(Map<Crew, AttendanceLog> attendanceRecord) {
        this.attendanceRecord = attendanceRecord;
    }

    public AttendanceLog findAttendanceLogByCrew(Crew crew){
        return attendanceRecord.get(crew);
    }

}
