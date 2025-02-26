package domain;

import java.time.LocalTime;

public class Attendance {
    public String attend(String nickname, LocalTime time) {
        if (time.isBefore(LocalTime.of(10, 5)) || time.equals(LocalTime.of(10, 5))) {
            return "출석";
        }
        if (time.isBefore(LocalTime.of(10, 30)) || time.equals(LocalTime.of(10, 30))) {
            return "지각";
        }
        return "결석";
    }
}
