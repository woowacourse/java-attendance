package attendance.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record AttendanceDate(LocalDate date) {

    public AttendanceDate {
        validateAttendanceDate(date);
    }

    public AttendanceDate(int year, int month, int day) {
        this(LocalDate.of(year, month, day));
        validateAttendanceDate(date);
    }

    private void validateAttendanceDate(LocalDate date) {
        if (!SystemDuration.isSystemDuration(date)) {
            throw new IllegalArgumentException(String.format("시스템 운영 기간은 %s ~ %s입니다.",
                    SystemDuration.startDate,
                    SystemDuration.endDate
            ));
        }
        if (!EducationDay.isDuringEducationDay(date)) {
            throw new IllegalArgumentException(date.format(DateTimeFormatter.ofPattern("MM월 dd일 EEE은 등교일이 아닙니다.")));
        }
    }
}
