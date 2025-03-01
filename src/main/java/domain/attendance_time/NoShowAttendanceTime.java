package domain.attendance_time;

import java.time.LocalTime;
import java.util.Optional;

public class NoShowAttendanceTime implements AttendanceTime {
    
    public NoShowAttendanceTime() {
    }
    
    @Override
    public Optional<LocalTime> getAttendTime() {
        return Optional.empty();
    }
}
