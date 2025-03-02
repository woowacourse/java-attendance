package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class AttendanceRecord {
    private static final Map<LocalDateTime, AttendanceRecord> CACHE = new ConcurrentHashMap<>();
    private static final Map<LocalDate, AttendanceRecord> EMPTY_CACHE = new ConcurrentHashMap<>();
    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;

    private AttendanceRecord(final LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = null;
    }

    public AttendanceRecord(final LocalDateTime attendanceDateTime) {
        this.attendanceDate = attendanceDateTime.toLocalDate();
        this.attendanceTime = attendanceDateTime.toLocalTime();
    }

    public static AttendanceRecord of(final LocalDateTime localDateTime) {
        return CACHE.computeIfAbsent(localDateTime, AttendanceRecord::new);
    }


    public static AttendanceRecord empty(final LocalDate attendanceDate) {
        return EMPTY_CACHE.computeIfAbsent(attendanceDate, AttendanceRecord::new);
    }

    public AttendanceStatus calculateAttendanceStatus() {
        if (attendanceTime == null) {
            return AttendanceStatus.ABSENCE;
        }
        return AttendanceStatus.calculateStatus(attendanceTime, attendanceDate.getDayOfWeek());
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public Optional<LocalTime> getAttendanceTime() {
        return Optional.ofNullable(attendanceTime);
    }
}
