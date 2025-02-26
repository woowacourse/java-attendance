package domain;

import dto.AttendanceStatusDTO;
import java.time.LocalTime;
import java.util.Objects;

public class AttendanceSheet {

    private final String nickname;
    private final AttendanceDateTime attendanceDateTime;

    public AttendanceSheet(String nickname, AttendanceDateTime attendanceDateTime) {
        this.nickname = nickname;
        this.attendanceDateTime = attendanceDateTime;
    }

    public boolean hasNickname(String nickname) {
        return this.nickname.equals(nickname);
    }

    public String getNickname() {
        return nickname;
    }

    public AttendanceDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AttendanceSheet that = (AttendanceSheet) object;
        return Objects.equals(getNickname(), that.getNickname()) && Objects.equals(
                getAttendanceDateTime(), that.getAttendanceDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNickname(), getAttendanceDateTime());
    }

    public boolean isSameDay(int day) {
        return attendanceDateTime.isSameDay(day);
    }

    public AttendanceStatusDTO getAttendanceStatus() {
        return new AttendanceStatusDTO(attendanceDateTime, attendanceDateTime.check());
    }

    public void updateTime(LocalTime updateTime) {
        attendanceDateTime.update(updateTime);
    }
}
