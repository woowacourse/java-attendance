package attendance.domain;

import attendance.exception.ExceptionMessage;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttendanceSystem {

    private final CrewStorage crewStorage;
    private final List<AttendanceRecord> records = new ArrayList<>();

    public AttendanceSystem(CrewStorage crewStorage) {
        this.crewStorage = crewStorage;
    }

    public void addAttendanceRecord(String crewNickname, LocalDateTime arrivalDateTime) {
        crewStorage.validateIsNotContained(crewNickname);
        Optional<AttendanceRecord> originRecord = findAttendanceRecord(crewNickname, arrivalDateTime.toLocalDate());
        if (originRecord.isPresent()) {
            throw new IllegalArgumentException(ExceptionMessage.ALREADY_ATTENDANCE.getMessage());
        }

        if (arrivalDateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            AttendanceType type = AttendanceType.parse(LocalTime.of(13, 0, 0), arrivalDateTime.toLocalTime());
            AttendanceRecord newRecord = new AttendanceRecord(crewNickname, arrivalDateTime, type);
            records.add(newRecord);
            return;
        }

        AttendanceType type = AttendanceType.parse(LocalTime.of(10, 0, 0), arrivalDateTime.toLocalTime());
        AttendanceRecord newRecord = new AttendanceRecord(crewNickname, arrivalDateTime, type);
        records.add(newRecord);
    }

    public Optional<AttendanceRecord> findAttendanceRecord(String crewNickname, LocalDate date) {
        return records.stream()
                .filter(record -> record.isSame(crewNickname, date))
                .findAny();
    }
}
