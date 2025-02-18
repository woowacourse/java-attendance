package attendance.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum Attendance {
    출석(),
    지각(),
    결석();


    public static Attendance from(LocalDateTime dateTime) {
        LocalTime nowTime = dateTime.toLocalTime();
        WoowaDayOfWeek dayOfWeek = WoowaDayOfWeek.from(dateTime);

        long duration = Duration.between(dayOfWeek.getStartTime(),nowTime).toMinutes();

        if (duration > 30) {
            return 결석;
        }
        if (duration > 5) {
            return 지각;
        }

        return 출석;
    }
}
