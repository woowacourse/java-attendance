package domain;

import dto.CheckAttendanceResponse;
import dto.ModifyAttendanceResponse;
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

    public ModifyAttendanceResponse modifyAttendance(LocalDate date, LocalTime modifiedTime) {
        LocalTime originalTime = attendances.get(date);
        attendances.put(date, modifiedTime);

        return new ModifyAttendanceResponse(
                date,
                originalTime,
                modifiedTime,
                "출석",
                "출석"
        );
    }
}
