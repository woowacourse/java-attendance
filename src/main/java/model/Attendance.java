package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {

    private final String nickname;
    private final LocalDate attendDate;
    private LocalTime attendTime;

    public Attendance(String nickname, LocalDate attendDate, LocalTime attendTime) {
        this.nickname = nickname;
        this.attendDate = attendDate;
        this.attendTime = attendTime;
    }

    public boolean isSameDate(String nickname, LocalDate attendDate) {
        return this.nickname.equals(nickname) && this.attendDate.isEqual(attendDate);
    }

    public void updateTime(LocalTime updateTime) {
        this.attendTime = updateTime;
    }

    public boolean isSameNickname(String nickname) {
        return this.nickname.equals(nickname);
    }

    public boolean isAbsent() {
        return attendTime.isAfter(LocalTime.of(10, 30));
    }

    public boolean isLate() {
        return attendTime.isAfter(LocalTime.of(10, 5));
    }

    public boolean isAttend() {
        return !isAbsent() && !isLate();
    }

    public boolean isBefore(LocalDate date) {
        return this.attendDate.isBefore(date);
    }
}
