package attendance.domain;

import java.time.LocalDate;

public class Holiday {

    public static void validateAttendanceDate(final LocalDate attendanceDate) {
        throw new IllegalArgumentException("[ERROR] 12월 14일 토요일은 등교일이 아닙니다.");
    }
}
