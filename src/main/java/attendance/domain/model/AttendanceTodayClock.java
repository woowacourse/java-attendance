package attendance.domain.model;

import java.time.LocalDate;

public class AttendanceTodayClock implements TodayClock {

    @Override
    public LocalDate getTodayDate() {
        return LocalDate.of(2024, 12, 13);
    }
}
