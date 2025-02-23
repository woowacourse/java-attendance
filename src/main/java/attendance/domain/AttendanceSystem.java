package attendance.domain;

import java.time.LocalDateTime;

public class AttendanceSystem {

    private final AttendanceManager attendanceManager;

    public AttendanceSystem(final AttendanceManager attendanceManager) {
        this.attendanceManager = attendanceManager;
    }

    public Attendance processAttendanceCheck(final LocalDateTime dateTime, final String nickname) {
        Attendances attendances = attendanceManager.findCrewAttendance(nickname);
        return attendances.checkAndUpdateAttendance(dateTime);
    }

    public Attendance processAttendanceUpdate(final LocalDateTime dateTime, final String nickname) {
        Attendances attendances = attendanceManager.findCrewAttendance(nickname);
        return attendances.updateAttendance(dateTime);
    }

    public void validateNicknameExists(String nickname) {
        if (!attendanceManager.containsNickname(nickname)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }
}
