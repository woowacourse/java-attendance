package domain;

import java.time.LocalTime;

public class Attendance {

    public String attend(String nickname, LocalTime time) {
        if (time.isBefore(LocalTime.of(10, 5))) {
            return "출석";
        }
        return null;
    }
}
