package domain;

import dto.CheckAttendanceResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Crew {
    private final String name;
    private final Map<LocalDate, LocalTime> attendances = new HashMap<>();

    public Crew(String name) {
        this.name = name;
    }

    public CheckAttendanceResponse checkAttendance(LocalDate date, LocalTime time) {
        validateNoDuplicateAttendance(date);
        attendances.put(date, time);

        return new CheckAttendanceResponse(
                date, time, "출석"
        );
    }

    private void validateNoDuplicateAttendance(LocalDate input) {
        if (attendances.containsKey(input)) {
            throw new IllegalArgumentException(ErrorCode.ATTENDANCE_DATE_DUPLICATED.getMessage());
        }
    }
}
