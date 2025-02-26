package domain;

import exception.AppException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class CheckInDateTime implements Comparable<CheckInDateTime> {
    private final LocalDate checkInDate;
    private final LocalTime checkInTime;

    public static final LocalTime CAMPUS_START_TIME = LocalTime.of(8, 0);
    public static final LocalTime CAMPUS_END_TIME = LocalTime.of(23, 0);

    private CheckInDateTime(LocalDate checkInDate, LocalTime checkInTime) {
        validateCampusTime(checkInTime);
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
    }

    public static CheckInDateTime of(LocalDate checkInDate, LocalTime checkInTime) {
        return new CheckInDateTime(checkInDate, checkInTime);
    }

    private void validateCampusTime(LocalTime checkInTime) {
        if (checkInTime.isBefore(CAMPUS_START_TIME) || checkInTime.isAfter(CAMPUS_END_TIME)) {
            throw new AppException("캠퍼스 이용 시간은 08시부터 23시까지 입니다.");
        }
    }

    @Override
    public int compareTo(CheckInDateTime o) {
        int dateComparison = this.checkInDate.compareTo(o.checkInDate);
        if (dateComparison != 0) {
            return dateComparison;
        }
        return this.checkInTime.compareTo(o.checkInTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckInDateTime that = (CheckInDateTime) o;
        return checkInDate.equals(that.checkInDate) && checkInTime.equals(that.checkInTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checkInDate, checkInTime);
    }
}
