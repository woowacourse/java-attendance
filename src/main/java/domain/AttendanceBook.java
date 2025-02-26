package domain;

import dto.InitialInfo;
import java.util.Map;

public class AttendanceBook {
    private final Map<CrewName, AttendanceRecord> value;

    public AttendanceBook(InitialInfo initialInfo) {
        this.value = initialInfo.getValue();
    }

    public void addAttendance(CrewName crewName, Attendance attendance) {
        AttendanceRecord attendanceRecord = findAttendanceRecordBy(crewName);
        attendanceRecord.add(attendance);
        value.put(crewName, attendanceRecord);
    }

    public AttendanceRecord findAttendanceRecordBy(CrewName crewName) {
        AttendanceRecord attendanceRecord = value.get(crewName);
        if (attendanceRecord == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 닉네임입니다.");
        }
        return attendanceRecord;
    }
}
