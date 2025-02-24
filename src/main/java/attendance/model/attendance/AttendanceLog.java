package attendance.model.attendance;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class AttendanceLog {

    private final LocalDate date;
    private final LocalTime time;
    private final AttendanceStatus attendanceStatus;

    public AttendanceLog(LocalDate date, LocalTime time, AttendanceStatus attendanceStatus) {
        this.date = date;
        this.time = time;
        this.attendanceStatus = attendanceStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceLog that = (AttendanceLog) o;
        return Objects.equals(date, that.date) && Objects.equals(time, that.time)
                && attendanceStatus == that.attendanceStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, attendanceStatus);
    }

    @Override
    public String toString() {
        return "AttendanceLog{" +
                "date=" + date +
                ", time=" + time +
                ", attendanceStatus=" + attendanceStatus +
                '}';
    }
}
