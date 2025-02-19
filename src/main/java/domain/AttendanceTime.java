package domain;

import java.time.LocalTime;
import java.util.List;

public enum AttendanceTime {

    MON_TIME(
            List.of(Calender.MON),
            List.of(LocalTime.of(13, 5),
                    LocalTime.of(13, 30))),
    ELSE_TIME(
            List.of(Calender.TUE, Calender.WED, Calender.THU, Calender.FRI),
            List.of(LocalTime.of(10, 5),
                    LocalTime.of(10, 30)));

    private final List<Calender> calenders;
    private final List<LocalTime> localTimes;

    AttendanceTime(final List<Calender> calenders, final List<LocalTime> localTimes) {
        this.calenders = calenders;
        this.localTimes = localTimes;
    }

    public List<Calender> getCalenders() {
        return calenders;
    }

    public List<LocalTime> getLocalTimes() {
        return localTimes;
    }
}
