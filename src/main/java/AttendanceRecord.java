import java.time.LocalDateTime;

public class AttendanceRecord {
    private final LocalDateTime dateTime;

    public AttendanceRecord(LocalDateTime dateTime) {
        validate(dateTime);
        this.dateTime = dateTime;
    }

    private void validate(LocalDateTime dateTime) {
        if (ClassSchedule.isDayOff(dateTime.toLocalDate())) {
            throw new IllegalArgumentException("[ERROR] 등교일이 아닙니다.");
        }
    }
}
