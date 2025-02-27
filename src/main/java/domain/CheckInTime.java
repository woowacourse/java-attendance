package domain;

import exception.AppException;

import java.time.LocalTime;

public class CheckInTime {
    private final LocalTime checkInTime;

    public static final LocalTime CAMPUS_START_TIME = LocalTime.of(8, 0);
    public static final LocalTime CAMPUS_END_TIME = LocalTime.of(23, 0);

    private CheckInTime(LocalTime checkInTime) {
        validateCampusTime(checkInTime);
        this.checkInTime = checkInTime;
    }

    public static CheckInTime of(LocalTime checkInTime) {
        return new CheckInTime(checkInTime);
    }

    public static CheckInTime of(int hour, int minute) {
        return new CheckInTime(LocalTime.of(hour, minute));
    }

    private void validateCampusTime(LocalTime checkInTime) {
        if (checkInTime.isBefore(CAMPUS_START_TIME) || checkInTime.isAfter(CAMPUS_END_TIME)) {
            throw new AppException("캠퍼스 이용 시간은 08시부터 23시까지 입니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckInTime that = (CheckInTime) o;
        return checkInTime.equals(that.checkInTime);
    }

    @Override
    public int hashCode() {
        return checkInTime.hashCode();
    }
}
