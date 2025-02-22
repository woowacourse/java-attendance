package domain;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public enum AttendanceTime {

    MON_TIME(
            List.of(Calender.MON),
            List.of(LocalTime.of(13, 5),
                    LocalTime.of(13, 30))),
    OTHER_TIME(
            List.of(Calender.TUE, Calender.WED, Calender.THU, Calender.FRI),
            List.of(LocalTime.of(10, 5),
                    LocalTime.of(10, 30)));

    private static final LocalTime IN_TIME = LocalTime.of(8, 0);
    private static final LocalTime OUT_TIME = LocalTime.of(23, 0);

    private final List<Calender> calenders;
    private final List<LocalTime> localTimes;

    AttendanceTime(final List<Calender> calenders, final List<LocalTime> localTimes) {
        this.calenders = calenders;
        this.localTimes = localTimes;
    }

    public static void validateCampusTime(final LocalTime localTime) {
        if (localTime.isBefore(IN_TIME) || localTime.isAfter(OUT_TIME)) {
            throw new IllegalArgumentException("캠퍼스 운영 시간은 매일 08:00~23:00입니다.");
        }
    }

    public static AttendanceTime findBy(final Calender calender) {
        return Arrays.stream(AttendanceTime.values())
                .filter(value -> value.calenders.contains(calender))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 요일 입니다."));
    }

    public List<Calender> getCalenders() {
        return calenders;
    }

    public List<LocalTime> getLocalTimes() {
        return localTimes;
    }

}
