package policy;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceSheet {
    private String nickname;
    private LocalDate date;
    private LocalTime time;

    public AttendanceSheet(String nickname, LocalDate localDate, LocalTime localTime) {
        this.nickname = nickname;
        this.date = localDate;
        this.time = localTime;
    }
}
