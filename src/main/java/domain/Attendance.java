package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public record Attendance(LocalDateTime dateAndTime) {
    public Attendance {
        validateRunningTime(dateAndTime);
    }

    public int getDayOfMonth() {
        return dateAndTime.getDayOfMonth();
    }

    public String getState() {
        return AttendanceStatus.of(dateAndTime.getHour(), dateAndTime.getMinute(), dateAndTime.getDayOfWeek())
                .getResult();
    }

    public boolean isEqualDate(LocalDateTime targetDateAndTime) {
        return dateAndTime.toLocalDate()
                .isEqual(targetDateAndTime.toLocalDate());
    }

    private void validateRunningTime(LocalDateTime dateAndTime) {
        DayOfWeek dayOfWeek = dateAndTime.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY || dateAndTime.getDayOfMonth() == 25) {
            throw new IllegalArgumentException("주말 또는 공휴일은 캠퍼스 휴장입니다.");
        }
    }
}
