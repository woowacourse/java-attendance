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

    private void validateCampusTime(LocalTime checkInTime) {
        if (checkInTime.isBefore(CAMPUS_START_TIME) || checkInTime.isAfter(CAMPUS_END_TIME)) {
            throw new AppException("캠퍼스 이용 시간은 08시부터 23시까지 입니다.");
        }
    }
}
