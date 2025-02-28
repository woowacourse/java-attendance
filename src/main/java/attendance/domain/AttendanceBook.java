package attendance.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AttendanceBook {
    private final Map<Crew, AttendanceLog> attendanceRecord;

    public AttendanceBook(Map<Crew, AttendanceLog> attendanceRecord) {
        this.attendanceRecord = attendanceRecord;
    }

    //1. 출석등록
    public Attendance registerAttendance(Crew crew, LocalDateTime newAttendanceDateTime) {
        validateCrewExistance(crew);
        AttendanceLog attendanceLog = attendanceRecord.get(crew);
        return attendanceLog.registerAttendance(newAttendanceDateTime);
    }

    //2. 출석수정
    public List<Attendance> modifyAttendance(Crew crew, LocalDateTime newALocalDateTime) {
        validateCrewExistance(crew);
        AttendanceLog attendanceLog = attendanceRecord.get(crew);
        return attendanceLog.modifyAttendanceRecord(newALocalDateTime);
    }

    //3. 출석 확인

    //4. 제적 위험자 확인

    private void validateCrewExistance(Crew crew) {
        if (attendanceRecord.get(crew) == null) {
            throw new IllegalArgumentException("등록되지 않는 크루입니다");
        }
    }
}
