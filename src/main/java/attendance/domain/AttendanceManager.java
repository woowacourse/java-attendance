package attendance.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import attendance.exception.AttendanceException;
import attendance.utility.StringUtility;

public class AttendanceManager {
    HashMap<String, Attendances> attendanceManager = new HashMap<>();
    private static final String CANNOT_BE_EMPTY_NICKNAME = "닉네임은 공백일 수 없습니다.";

    public void addAttendance(String nickname, LocalDateTime time) {
        validateNickname(nickname);
        if (nickname == null || nickname.isEmpty() || nickname.isBlank()) {
            throw new AttendanceException(CANNOT_BE_EMPTY_NICKNAME);
        }
        AttendanceStatus attendanceStatus = AttendanceStatus.of("출석");

        attendanceManager.put(nickname, new Attendance(time, attendanceStatus));
    }

    private void validateNickname(String nickname) {
        if (StringUtility.isEmpty(nickname)) {
            throw new AttendanceException(CANNOT_BE_EMPTY_NICKNAME);
        }
    }

    public boolean isAttendanceExist(String nickname) {
        return attendanceManager.containsKey(nickname);
    }

    public LocalDateTime getAttendanceTime(String nickname) {
        Attendance attendance = attendanceManager.get(nickname);
        return attendance.getAttendanceTime();
    }

    public AttendanceStatus getAttendanceStatus(String nickname) {
        Attendance attendance = attendanceManager.get(nickname);
        return attendance.getAttendanceStatus();
    }
}
