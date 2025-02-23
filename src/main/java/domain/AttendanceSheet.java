package domain;

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

    public boolean isCorrectDay(AttendanceSheet attendanceSheet) {
        return this.attendanceDateTime.isCorrectDay(attendanceSheet);
    }

    public boolean isCorrectDay(int day) {
        return attendanceDateTime.isCorrectDay(day);
    }
}
