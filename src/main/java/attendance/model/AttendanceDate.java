package attendance.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record AttendanceDate(LocalDate date) {

    public AttendanceDate(int year, int month, int day) {
        this(LocalDate.of(year, month, day));
        if (!EducationDay.isDuringEducationDay(date)) {
            throw new IllegalArgumentException(date.format(DateTimeFormatter.ofPattern("MM월 dd일 EEE은 등교일이 아닙니다.")));
        }
    }
}
