package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record AttendanceDate(
        LocalDate localDate
) {
    public AttendanceDate {
        validate(localDate);
    }

    public DayOfWeek getDayOfWeek() {
        return localDate.getDayOfWeek();
    }

    private void validate(LocalDate localDate) {
        if (!WoowaDurationTime.isDurationDay(localDate)) {
            throw new IllegalArgumentException(localDate.format(
                    DateTimeFormatter.ofPattern("MM월 dd일 EEE은 등교일이 아닙니다.")
            ));
        }
    }
}
