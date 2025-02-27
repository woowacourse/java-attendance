package attendance.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class AttendanceRegister {
    private final Map<String, AttendanceRecord> register = new HashMap<>();

    public void attend(String crewName, AttendanceDate attendanceDate, LocalTime attendanceTime) {
        validateDuringEducationTime(attendanceDate, attendanceTime);
        AttendanceRecord attendanceRecord = register.getOrDefault(crewName, new AttendanceRecord());
        validateExistAttendance(attendanceDate, attendanceRecord);
        attendanceRecord.add(new AttendanceDateTime(attendanceDate, attendanceTime));
        register.putIfAbsent(crewName, attendanceRecord);
    }

    public AttendanceDateTime findAttendanceDateTimeByCrewName(String crewName, AttendanceDate attendanceDate) {
        validateContainsCrewName(crewName);
        return register.get(crewName).findAttendanceByDate(attendanceDate);
    }

    public void modify(String crewName, AttendanceDate modifyDate, LocalTime modifyTime) {
        validateModifyDate(crewName, modifyDate);
        validateDuringEducationTime(modifyDate, modifyTime);
        validateContainsCrewName(crewName);
        AttendanceRecord attendanceRecord = register.get(crewName);
        AttendanceDateTime foundAttendanceDateTime = attendanceRecord.findAttendanceByDate(modifyDate);
        foundAttendanceDateTime.modifyAttendanceTime(modifyTime);
    }

    private void validateDuringEducationTime(AttendanceDate attendanceDate, LocalTime attendanceTime) {
        if (!CampusOpenTime.isDurationTime(attendanceDate.date(), attendanceTime)) {
            throw new IllegalArgumentException(
                    attendanceTime.format(DateTimeFormatter.ofPattern("HH시 mm분은 등교시간이 아닙니다.")
                    ));
        }
    }

    private void validateExistAttendance(AttendanceDate attendanceDate, AttendanceRecord attendanceRecord) {
        if (attendanceRecord.containsAttendanceDateTimeByDate(attendanceDate)) {
            throw new IllegalArgumentException("이미 출석한 날짜입니다.");
        }
    }

    private void validateContainsCrewName(String name) {
        if (!register.containsKey(name)) {
            throw new IllegalArgumentException("존재하지 않는 크루입니다.");
        }
    }

    private void validateModifyDate(String crewName, AttendanceDate modifyDate) {
        if (!register.get(crewName).containsAttendanceDateTimeByDate(modifyDate)) {
            throw new IllegalArgumentException("출석내역이 없는 날짜입니다.");
        }
    }

    public AttendanceRecord findAttendanceRecordByCrewName(String crewName) {
        return register.get(crewName);
    }

    public Stream<Entry<String, AttendanceRecord>> entryStream() {
        return register.entrySet().stream();
    }
}
