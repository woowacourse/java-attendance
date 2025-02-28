package attendance.domain;

import java.time.LocalDateTime;
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
    public void registerAttendance(Crew newCrew, LocalDateTime newAttendanceDateTime) {

    }


    //2. 출석수정

    //3. 출석 확인

    //4. 제적 위험자 확인

}
