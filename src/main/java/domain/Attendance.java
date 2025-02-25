package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {
    private String nickname;
    private LocalDate date;
    private LocalTime time;

    public Attendance(String nickname, LocalDate localDate, LocalTime localTime) {
        this.nickname = nickname;
        this.date = localDate;
        this.time = localTime;
    }

    public boolean isAlreadyAttendance(String nickname, LocalDate date) {
        return this.nickname.equals(nickname) && this.date.equals(date);
    }
}
