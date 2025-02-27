package model;

import java.time.DayOfWeek;
import java.util.Arrays;

enum BusinessHours {

    MONDAY(AttendanceTime.of(13, 0), AttendanceTime.of(18, 0)),
    TUESDAY(AttendanceTime.of(10, 0), AttendanceTime.of(18, 0)),
    WEDNESDAY(AttendanceTime.of(10, 0), AttendanceTime.of(18, 0)),
    THURSDAY(AttendanceTime.of(10, 0), AttendanceTime.of(18, 0)),
    FRIDAY(AttendanceTime.of(10, 0), AttendanceTime.of(18, 0));

    private static final AttendanceTime OperatingStartTime = AttendanceTime.of(8, 0);
    private static final AttendanceTime OperatingEndTime = AttendanceTime.of(23, 0);

    private final AttendanceTime startTime;
    private final AttendanceTime endTime;

    BusinessHours(final AttendanceTime startTime, final AttendanceTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static BusinessHours find(final AttendanceDateTime dateTime) {
        if (withInOperatingTime(dateTime)) {
            throw new IllegalArgumentException("영업시간이 아닌 시간에 출석을 할 수 없습니다.");
        }

        final DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        return Arrays.stream(BusinessHours.values())
                .filter(value -> value.equalsName(dayOfWeek.name()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("주말에는 영업하지 않습니다"));
    }

    private static boolean withInOperatingTime(final AttendanceDateTime dateTime) {
        return dateTime.getTime().isBefore(OperatingStartTime.getTime()) || dateTime.getTime()
                .isAfter(OperatingEndTime.getTime());
    }

    public boolean equalsName(final String name) {
        return name().equals(name);
    }

    public AttendanceTime getStartTime() {
        return startTime;
    }
}
