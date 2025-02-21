package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public record AttendanceHistory(LocalDate date, Optional<LocalTime> time, AttendanceStatus status) {
    public static AttendanceHistory of(LocalDate date, Optional<Attendance> attendance) {
        if (attendance.isPresent()) {
            Attendance extractedAttendance = attendance.get();
            return new AttendanceHistory(
                    date,
                    Optional.of(extractedAttendance.getTime().toLocalTime()),
                    extractedAttendance.getStatus()
            );
        }
        return new AttendanceHistory(
                date,
                Optional.empty(),
                AttendanceStatus.ABSENCE
        );
    }
}
