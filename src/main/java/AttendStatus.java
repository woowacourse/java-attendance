import java.time.LocalTime;

public enum AttendStatus {
    ATTEND, LATE, ABSENCE;

    public static AttendStatus calculateAttend(Attend attend, LocalTime lateTime, LocalTime absenceTime) {
        LocalTime targetTime = attend.time.toLocalTime();
        if (targetTime.isAfter(absenceTime)) {
            return ABSENCE;
        }
        if (targetTime.isAfter(lateTime)) {
            return LATE;
        }
        return ATTEND;
    }
}
