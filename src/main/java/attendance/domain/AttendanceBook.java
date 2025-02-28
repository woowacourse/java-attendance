package attendance.domain;

import java.util.Map;

public class AttendanceBook {
    private final Map<Crew, AttendanceLog> attendanceRecord;

    public AttendanceBook(Map<Crew, AttendanceLog> attendanceRecord) {
        this.attendanceRecord = attendanceRecord;
    }

    public AttendanceLog findAttendanceLogByCrew(Crew crew){
        return attendanceRecord.get(crew);
    }

    //1. 출석등록

    //2. 출석수정

    //3.

}
