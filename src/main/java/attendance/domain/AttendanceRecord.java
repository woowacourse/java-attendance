package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

public final class AttendanceRecord {

    private final String nickname;
    private final LocalDateTime arrivalDateTime;
    private final AttendanceStatusType type;

    public AttendanceRecord(String nickname, LocalDateTime arrivalDateTime, AttendanceStatusType type) {
        this.nickname = nickname;
        this.arrivalDateTime = arrivalDateTime;
        this.type = type;
    }

    public boolean checkSameDate(LocalDate date) {
        return arrivalDateTime.toLocalDate().equals(date);
    }

    public boolean isExpulsion() {
        return type == AttendanceStatusType.EXPULSION;
    }

    public boolean isInMonth(Month month) {
        return arrivalDateTime.getMonth() == month;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AttendanceRecord record = (AttendanceRecord) object;
        return Objects.equals(nickname, record.nickname) && Objects.equals(arrivalDateTime,
                record.arrivalDateTime) && type == record.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, arrivalDateTime, type);
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDate getDate() {
        return arrivalDateTime.toLocalDate();
    }

    public AttendanceStatusType getType() {
        return type;
    }
}
