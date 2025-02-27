package attendance.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class AttendanceRegister {
    private final Map<String, AttendanceRecord> register = new HashMap<>();

    public void attend(String crewName, LocalDate attendanceDate, LocalTime attendanceTime) {
        if (!EducationDay.isDuringEducationDay(attendanceDate)) {
            throw new IllegalArgumentException(
                    attendanceDate.format(DateTimeFormatter.ofPattern("MM월 dd일 EEE은 등교일이 아닙니다.")
                    ));
        }
        AttendanceRecord attendanceRecord = register.getOrDefault(crewName, new AttendanceRecord());
        validateExistAttendance(attendanceDate, attendanceRecord);
        attendanceRecord.add(new AttendanceDateTime(attendanceDate, attendanceTime));
        register.putIfAbsent(crewName, attendanceRecord);
    }

    public AttendanceDateTime findAttendanceDateTimeByCrewName(String crewName, LocalDate attendanceDate) {
        validateContainsCrewName(crewName);
        return register.get(crewName).findAttendanceByDate(attendanceDate);
    }

    public void modify(String crewName, LocalDate modifyDate, LocalTime modifyTime) {
        validateContainsCrewName(crewName);
        AttendanceRecord attendanceRecord = register.get(crewName);
        AttendanceDateTime foundAttendanceDateTime = attendanceRecord.findAttendanceByDate(modifyDate);
        foundAttendanceDateTime.modifyAttendanceTime(modifyTime);
    }

    private static void validateExistAttendance(LocalDate attendanceDate, AttendanceRecord attendanceRecord) {
        if (attendanceRecord.containsAttendanceDateTimeByDate(attendanceDate)) {
            throw new IllegalArgumentException("이미 출석한 날짜입니다.");
        }
    }

    private void validateContainsCrewName(String name) {
        if (!register.containsKey(name)) {
            throw new IllegalArgumentException("존재하지 않는 크루입니다.");
        }
    }
}
