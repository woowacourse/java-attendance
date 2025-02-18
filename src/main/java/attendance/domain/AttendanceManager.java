package attendance.domain;

import java.time.LocalDateTime;
import java.util.HashMap;

import attendance.dto.AttendanceDateDto;
import attendance.exception.AttendanceException;
import attendance.utility.StringUtility;

public class AttendanceManager {

    private final String NICKNAME_NOT_EXISTS = "출석 정보가 존재하지 않습니다.";
    private final String CANNOT_BE_EMPTY_NICKNAME = "닉네임은 공백일 수 없습니다.";

    private HashMap<String, Attendances> attendanceManager = new HashMap<>();

    public void addAttendance(String nickname, LocalDateTime time) {
        validateNickname(nickname);
        if (nickname == null || nickname.isEmpty() || nickname.isBlank()) {
            throw new AttendanceException(CANNOT_BE_EMPTY_NICKNAME);
        }
        AttendanceStatus attendanceStatus = AttendanceStatus.of("출석");

        Attendances attendances = attendanceManager.getOrDefault(nickname, new Attendances());

        attendanceManager.put(nickname, attendances);
        attendances.addAttendance(time, attendanceStatus);
    }

    private Attendances findAttendances(String nickname) {
        Attendances attendances = attendanceManager.get(nickname);
        if (attendances == null) {
            throw new AttendanceException(NICKNAME_NOT_EXISTS);
        }
        return attendances;
    }

    private void validateNickname(String nickname) {
        if (StringUtility.isEmpty(nickname)) {
            throw new AttendanceException(CANNOT_BE_EMPTY_NICKNAME);
        }
    }

    public AttendanceDateDto getAttendanceResult(String nickname, LocalDateTime attendanceTime) {
        var attendances = findAttendances(nickname);
        return attendances.getAttendanceTime(attendanceTime.toLocalDate());
    }
}
