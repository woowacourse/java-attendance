package domain;

import static domain.AttendanceStatus.ABSENT_MINUTE;
import static domain.AttendanceStatus.LATE_MINUTE;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendTime {

    private final LocalDate localDate;
    private final LocalTime localTime;

    public AttendTime(LocalDate localDate, LocalTime localTime) {
        validate(localDate);
        this.localDate = localDate;
        this.localTime = localTime;
    }

    public void validate(LocalDate localDate) {
        validateWeekend(localDate);
        validateHoliday(localDate);
    }

    private void validateWeekend(LocalDate localDate) {
        if (December.isWeekend(localDate.getDayOfMonth())) {
            throw new IllegalArgumentException("주말은 출석 할 수 없습니다.");
        }
    }

    private void validateHoliday(LocalDate localDate) {
        if (December.isHoliday(localDate.getDayOfMonth())) {
            throw new IllegalArgumentException("휴일은 출석 할 수 없습니다.");
        }
    }

    public AttendanceStatus checkAttendanceStatus() {
        int startingHour = AttendTimeOfWeekDay.getTimeByDayOfWeekDay(localDate.getDayOfWeek().getValue());
        return AttendanceStatus.calculateStatus(LocalTime.of(startingHour, LATE_MINUTE),
                LocalTime.of(startingHour, ABSENT_MINUTE), localTime);
    }

    public boolean checkSameDate(int date) {
        return localDate.getDayOfMonth() == date;
    }

    public String getAttendanceStatus() {
        return checkAttendanceStatus().getStatus();
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }
}
