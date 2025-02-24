package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public final class AttendanceRecord {

    private final String nickname;
    private final LocalDateTime arrivalDateTime;

    public AttendanceRecord(String nickname, LocalDateTime arrivalDateTime) {
        this.nickname = nickname;
        this.arrivalDateTime = arrivalDateTime;
    }

    public boolean isSame(String name, LocalDate date) {
        return nickname.equals(name) && arrivalDateTime.toLocalDate().equals(date);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AttendanceRecord that = (AttendanceRecord) object;
        return Objects.equals(nickname, that.nickname) && Objects.equals(arrivalDateTime,
                that.arrivalDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, arrivalDateTime);
    }
}
