package domain;

import domain.policy.AttendanceState;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {
    private String nickname;
    private LocalDate date;
    private LocalTime time;
    private AttendanceState state;

    public Attendance(String nickname, LocalDate localDate, LocalTime localTime, AttendanceState state) {
        this.nickname = nickname;
        this.date = localDate;
        this.time = localTime;
        this.state = state;
    }

    public boolean isAttendanceExist(String nickname, LocalDate date) {
        return this.nickname.equals(nickname) && this.date.equals(date);
    }

    public void update(LocalTime updateTime) {
        this.time = updateTime;
    }

    public boolean isSameNickname(String nickname) {
        return this.nickname.equals(nickname);
    }

    public AttendanceState getState() {
        return state;
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
