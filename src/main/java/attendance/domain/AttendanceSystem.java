package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class AttendanceSystem {

    public void addAttendanceRecord(String crewNickname, LocalDateTime arrivalDateTime) {

    }

    public Optional<AttendanceRecord> findAttendanceRecord(String crewNickname, LocalDate localDate) {
        return Optional.empty();
    }
}
