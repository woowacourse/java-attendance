package domain.dto;

import java.time.LocalDateTime;

public class AttendanceRecordDto {
    private final String nickname;
    private final LocalDateTime attendanceTime;

    public AttendanceRecordDto(String nickname, LocalDateTime attendanceTime) {
        this.nickname = nickname;
        this.attendanceTime = attendanceTime;
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }
}
