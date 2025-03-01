package attendance.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceHistory {

    private final Map<String, AttendanceTimes> attendanceHistory;

    private AttendanceHistory(List<String> fileReadResult) {
        this.attendanceHistory = new HashMap<>();
        initAttendanceHistory(fileReadResult);
    }

    public static AttendanceHistory create(List<String> fileReadResult) {
        return new AttendanceHistory(fileReadResult);
    }

    private void initAttendanceHistory(List<String> fileReadResult) {
        for (String file : fileReadResult) {
            String[] split = file.split(",");
            String nickname = split[0];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(split[1], formatter);
            AttendanceTime attendanceTime = AttendanceTime.from(dateTime);
            add(nickname, attendanceTime);
        }
    }

    public void add(String nickname, AttendanceTime attendanceTime) {
        attendanceHistory.computeIfAbsent(nickname, k -> AttendanceTimes.create());
        AttendanceTimes attendanceTimes = attendanceHistory.get(nickname);
        validateAlreadyAttendance(attendanceTimes.add(attendanceTime));
    }

    public void isValidCrew(String name) {
        if(attendanceHistory.get(name) == null) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }

    public AttendanceTimes getAttendanceTimesByName(String nickname) {
        return attendanceHistory.get(nickname);
    }

    public Map<String, AttendanceTimes> getAttendanceHistory() {
        return Collections.unmodifiableMap(attendanceHistory);
    }

    public AttendanceTime getAttendanceTimeByDate(String nickname, int findDate) {
        AttendanceTimes attendanceTimes = attendanceHistory.get(nickname);
        return attendanceTimes.findAttendanceByDate(findDate);
    }

    public AttendanceTime modifyAttendance(String nickname, AttendanceTime attendanceTime, LocalDateTime modifyTime) {
        AttendanceTimes attendanceTimes = attendanceHistory.get(nickname);
        return attendanceTimes.modifyAttendance(attendanceTime, modifyTime);
    }

    private void validateAlreadyAttendance(boolean isAdded) {
        if (!isAdded) {
            throw new IllegalArgumentException("해당 날짜에 이미 출석했습니다. 수정 기능을 이용해주세요.");
        }
    }
}
