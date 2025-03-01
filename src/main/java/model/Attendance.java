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
}
