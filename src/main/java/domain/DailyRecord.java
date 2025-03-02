package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class DailyRecord {

    private final LocalTime attendedTime;
    private final AttendanceStatus status;

    public DailyRecord(DayOfWeek dayOfWeek, LocalTime attendedTime) {
        this.attendedTime = attendedTime;
        this.status = AttendanceStatus.of(dayOfWeek, attendedTime);
    }

    public String getFormattedTime() {
        // TODO: null 값을 처리하여 시간의 String 타입을 반환
        return null;
    }

    public LocalTime getAttendedTime() {
        return attendedTime;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object obj) {
        DailyRecord other = (DailyRecord) obj;
        return attendedTime.equals(other.attendedTime);
    }
}
