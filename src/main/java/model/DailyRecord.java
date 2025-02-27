package model;

import java.time.LocalTime;

public class DailyRecord {

    private final LocalTime attendedTime;
    private final AttendanceStatus status;

    public DailyRecord(LocalTime attendedTime, AttendanceStatus status) {
        this.attendedTime = attendedTime;
        this.status = status;
    }
}
