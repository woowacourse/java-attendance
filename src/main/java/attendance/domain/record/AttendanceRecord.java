package attendance.domain.record;

import attendance.domain.checker.AttendanceType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

public final class AttendanceRecord {

    private final String nickname;
    private final LocalDateTime arrivalDateTime;
    private final AttendanceType attendanceType;

    public AttendanceRecord(String nickname, LocalDateTime arrivalDateTime, AttendanceType attendanceType) {
        this.nickname = nickname;
        this.arrivalDateTime = arrivalDateTime;
        this.attendanceType = attendanceType;
    }

    public boolean isSame(String name, LocalDate date) {
        return nickname.equals(name) && arrivalDateTime.toLocalDate().equals(date);
    }

    public boolean checkNickname(String name) {
        return nickname.equals(name);
    }

    public boolean checkIsInMonth(int year, Month month) {
        return arrivalDateTime.getYear() == year &&
                arrivalDateTime.getMonth() == month;
    }

    public boolean checkType(AttendanceType type) {
        return this.attendanceType == type;
    }

    public String getNickname() {
        return nickname;
    }

    public AttendanceType getAttendanceType() {
        return attendanceType;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
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
