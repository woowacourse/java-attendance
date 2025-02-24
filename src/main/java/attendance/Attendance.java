package attendance;

import java.util.Objects;

public class Attendance {

    public String attend(String nickname, String timeInput) {
        if(Objects.equals("--:--", timeInput)) {
            return "결석";
        }
        String[] time = timeInput.split(":");

        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);

        if(hour > 10 || minute > 30) {
            return "결석";
        }
        if(minute > 5) {
            return "지각";
        }
        return "출석";
    }
}
