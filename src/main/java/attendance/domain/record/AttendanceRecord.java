package attendance.domain.record;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Objects;

public final class AttendanceRecord {

    private final String nickname;
    private final LocalDateTime arrivalDateTime;
    private final AttendanceType type;

    public AttendanceRecord(String nickname, LocalDateTime arrivalDateTime, AttendanceType type) {
        this.nickname = nickname;
        this.arrivalDateTime = arrivalDateTime;
        this.type = type;
    }

    public boolean checkSameDate(LocalDate date) {
        return arrivalDateTime.toLocalDate().equals(date);
    }

    public boolean isExpulsion() {
        return type == AttendanceType.EXPULSION;
    }

    public boolean isInMonth(int year, Month month) {
        return arrivalDateTime.getYear() == year && arrivalDateTime.getMonth() == month;
    }

    public boolean isInPeriod(LocalDate startDate, LocalDate endDate) {
        LocalDate date = arrivalDateTime.toLocalDate();
        boolean isAfterOrEqual = date.isAfter(startDate) || date.equals(startDate);
        boolean isBeforeOrEqual = date.isBefore(endDate) || date.equals(endDate);
        return isAfterOrEqual && isBeforeOrEqual;
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

    public LocalTime getTime() {
        return arrivalDateTime.toLocalTime();
    }

    public AttendanceType getType() {
        return type;
    }
}
