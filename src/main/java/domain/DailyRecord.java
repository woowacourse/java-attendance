package domain;

import java.time.LocalTime;

public class DailyRecord {

    private final LocalTime attendedTime;
    private final AttendanceStatus status;

    public DailyRecord(LocalTime attendedTime, AttendanceStatus status) {
        this.attendedTime = attendedTime;
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        DailyRecord other = (DailyRecord) obj;
        return attendedTime.equals(other.attendedTime);
    }
}
