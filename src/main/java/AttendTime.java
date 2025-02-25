import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendTime {
    private final LocalDateTime localDateTime;

    public AttendTime(LocalDateTime localDateTime) {
        validate(localDateTime);
        this.localDateTime = localDateTime;
    }

    public void validate(LocalDateTime localDateTime) {
        validateWeekend(localDateTime);
        validateHolyDay(localDateTime);
    }

    private void validateWeekend(LocalDateTime localDateTime) {
        if (localDateTime.getDayOfWeek().getValue() > 5)
            throw new IllegalArgumentException("주말은 출석 할 수 없습니다.");
    }

    private void validateHolyDay(LocalDateTime localDateTime) {
        if (December.checkHolyDay(localDateTime.getDayOfMonth())) {
            throw new IllegalArgumentException("휴일은 출석 할 수 없습니다.");
        }
    }

    public AttendanceStatus checkAttendanceStatus() {
        int startingHour = AttendTimeOfWeekDay.getTimeByDayOfWeekDay(localDateTime.getDayOfWeek().getValue());
        return AttendanceStatus.calculate(LocalTime.of(startingHour, 5), LocalTime.of(startingHour, 30), localDateTime.toLocalTime());
    }

    @Override
    public boolean equals(Object obj) {
        return obj.equals(this.localDateTime);
    }

}
