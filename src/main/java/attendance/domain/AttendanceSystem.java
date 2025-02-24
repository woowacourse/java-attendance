package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttendanceSystem {

    private List<AttendanceRecord> records = new ArrayList<>();

    public void addAttendanceRecord(String crewNickname, LocalDateTime arrivalDateTime) {
        AttendanceRecord newRecord = new AttendanceRecord(crewNickname, arrivalDateTime);
        records.add(newRecord);
    }

    public Optional<AttendanceRecord> findAttendanceRecord(String crewNickname, LocalDate date) {
        return records.stream()
                .filter(record -> record.isSame(crewNickname, date))
                .findAny();
    }
}
