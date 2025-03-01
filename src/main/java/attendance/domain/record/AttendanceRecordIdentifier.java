package attendance.domain.record;

import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

public final class AttendanceRecordIdentifier {

    private final String nickname;
    private final LocalDate date;

    public AttendanceRecordIdentifier(String nickname, LocalDate date) {
        this.nickname = nickname;
        this.date = date;
    }

    public AttendanceRecordIdentifier(AttendanceRecord record) {
        this.nickname = record.getNickname();
        this.date = record.getArrivalDateTime().toLocalDate();
    }

    public boolean checkNickname(String name) {
        return nickname.equals(name);
    }

    public boolean checkIsInMonth(int year, Month month) {
        return date.getYear() == year &&
                date.getMonth() == month;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AttendanceRecordIdentifier that = (AttendanceRecordIdentifier) object;
        return Objects.equals(nickname, that.nickname) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, date);
    }
}
