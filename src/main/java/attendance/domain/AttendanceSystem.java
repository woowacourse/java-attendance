package attendance.domain;

import attendance.exception.ExceptionMessage;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class AttendanceSystem {

    private final CrewStorage crewStorage;
    private final HolidayChecker holidayChecker;
    private final List<AttendanceRecord> records = new ArrayList<>();

    public AttendanceSystem(CrewStorage crewStorage, HolidayChecker holidayChecker) {
        this.crewStorage = crewStorage;
        this.holidayChecker = holidayChecker;
    }

    public void addAttendanceRecord(String crewNickname, LocalDateTime arrivalDateTime) {
        crewStorage.validateIsNotContained(crewNickname);

        Optional<AttendanceRecord> originRecord = findAttendanceRecord(crewNickname, arrivalDateTime.toLocalDate());
        if (originRecord.isPresent()) {
            throw new IllegalArgumentException(ExceptionMessage.ALREADY_ATTENDANCE.getMessage());
        }

        if (holidayChecker.checkHoliday(arrivalDateTime.toLocalDate())) {
            String exceptionMessage = String.format(ExceptionMessage.HOLIDAY_ATTENDANCE.getMessage(),
                    arrivalDateTime.getMonth().getValue(), arrivalDateTime.getDayOfMonth(),
                    arrivalDateTime.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREA));
            throw new IllegalArgumentException(exceptionMessage);
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
