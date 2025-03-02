package attendance.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceBook {
    private final Map<Crew, AttendanceLog> attendanceRecord;

    public AttendanceBook(Map<Crew, AttendanceLog> attendanceRecord) {
        this.attendanceRecord = attendanceRecord;
    }

    public AttendanceLog findAttendanceLogByCrew(Crew crew) {
        return attendanceRecord.get(crew);
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
    public List<Attendance> checkAttendancesRecord(Crew crew) {
        validateCrewExistance(crew);
        AttendanceLog attendanceLog = attendanceRecord.get(crew);
        return attendanceLog.checkAttendancesRecord();
    }

    public AttendanceStatus checkAttendanceCrewStatus(Crew crew) {
        AttendanceLog attendanceLog = attendanceRecord.get(crew);
        int attendanceCount = attendanceLog.countAttendanceStatus(Subject.ATTENDANCE);
        int lateCount = attendanceLog.countAttendanceStatus(Subject.LATE);
        int absentCount = attendanceLog.countAttendanceStatus(Subject.ABSENT);
        return new AttendanceStatus(attendanceCount, lateCount, absentCount);
    }

    // 4. 제적 위험자 확인
    public Map<Crew, AttendanceStatus> checkExpelledCrews() {
        return attendanceRecord.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> {
                    AttendanceLog attendanceLog = entry.getValue();
                    int attendanceCount = attendanceLog.countAttendanceStatus(Subject.ATTENDANCE);
                    int lateCount = attendanceLog.countAttendanceStatus(Subject.LATE);
                    int absentCount = attendanceLog.countAttendanceStatus(Subject.ABSENT);
                    return new AttendanceStatus(attendanceCount, lateCount, absentCount);
                }
            ));
    }



    private void validateCrewExistance(Crew crew) {
        if (attendanceRecord.get(crew) == null) {
            throw new IllegalArgumentException("등록되지 않는 크루입니다");
        }
    }
}
