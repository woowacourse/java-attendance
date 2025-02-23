package domain;

import java.time.LocalTime;

public enum OperationTime {
    START_TIME(LocalTime.of(8, 0)),
    LATE_TIME(LocalTime.of(10, 5)),
    ABSENCE_TIME(LocalTime.of(10, 30)),
    END_TIME(LocalTime.of(23, 0));

    private final LocalTime time;

    OperationTime(LocalTime time) {
        this.time = time;
    }

    public static void validateAttendableDay(Attend attend) {
        if (attend.isDayOff()) {
            throw new IllegalArgumentException("쉬는날은 출석할 수 없음");
        }
    }

    public static void validateAttendableTime(Attend attend) {
        if (attend.isTimeOff(START_TIME.time, END_TIME.time)) {
            throw new IllegalArgumentException("운영 시간 외에는 출석할 수 없음");
        }
    }

    public static void validateDay(int day) {
        int lengthOfMonth = Current.TODAY.getLengthOfMonth();
        if (day < 1 || day > lengthOfMonth) {
            throw new IllegalArgumentException(String.format("day는 1 이상 %d 이하여야 함", lengthOfMonth));
        }
    }

    public LocalTime getTime() {
        return time;
    }
}
