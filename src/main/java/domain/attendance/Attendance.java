package domain.attendance;

import java.time.LocalDateTime;
import java.util.Objects;

public class Attendance {
    private final LocalDateTime time;
    private final AttendanceStatus status;

    public Attendance(LocalDateTime time) {
        this.time = time;
        this.status = AttendanceStatus.of(time);
    }

    public LocalDateTime getTime() {
        return time;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public Attendance modify(int newHour, int newMinutes) { //TODO : 이름 수정
        return new Attendance(time.withHour(newHour).withMinute(newMinutes));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Attendance other = (Attendance) obj;
        return time.isEqual(other.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(time);
    }
}
