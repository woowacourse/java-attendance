package domain.attendancetime;

import java.time.LocalTime;
import java.util.Optional;

public interface AttendanceTime {
    
    Optional<LocalTime> getAttendTime();
}
