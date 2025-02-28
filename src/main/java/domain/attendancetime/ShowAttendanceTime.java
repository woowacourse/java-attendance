package domain.attendancetime;

import java.time.LocalTime;
import java.util.Optional;

public class ShowAttendanceTime implements AttendanceTime {
    
    private static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23, 0);
    
    private final LocalTime attendTime;
    
    public ShowAttendanceTime(final LocalTime attendTime) {
        validateCampusOpen(attendTime);
        this.attendTime = attendTime;
    }
    
    private void validateCampusOpen(final LocalTime time) {
        if (time != null && !isCampusOpen(time)) {
            throw new IllegalArgumentException("캠퍼스 운영시간이 아닙니다. (08:00~23:00)");
        }
    }
    
    private boolean isCampusOpen(final LocalTime time) {
        return !time.isBefore(CAMPUS_OPEN_TIME) && !time.isAfter(CAMPUS_CLOSE_TIME);
    }
    
    @Override
    public Optional<LocalTime> getAttendTime() {
        return Optional.of(attendTime);
    }
}
