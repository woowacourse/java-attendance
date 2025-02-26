package domain;

import dto.InitialInfo;
import java.util.Map;

public class AttendanceBook {
    private final Map<CrewName, AttendanceRecord> value;

    public AttendanceBook(InitialInfo initialInfo) {
        this.value = initialInfo.getValue();
    }

    public Attendance addAttendance(CrewName crewName, Attendance attendance) {
        AttendanceRecord attendanceRecord = findAttendanceRecordBy(crewName);
        if(attendanceRecord.contains(attendance)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석 기록이 존재합니다. 수정 메뉴를 이용해주세요.");
        }
        attendanceRecord.add(attendance);
        value.put(crewName, attendanceRecord);
        return attendance;
    }

    public AttendanceRecord findAttendanceRecordBy(CrewName crewName) {
        AttendanceRecord attendanceRecord = value.get(crewName);
        if (attendanceRecord == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 닉네임입니다.");
        }
        return attendanceRecord;
    }
}
