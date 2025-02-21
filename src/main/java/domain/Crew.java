package domain;

import dto.AttendanceRecordResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import utils.TimeUtils;
import view.ErrorCode;

public class Crew {
    private static final int END_DAY_OF_DECEMBER = 31;
    private static final int START_DAY_OF_DECEMBER = 1;

    private final String name;
    private final Map<LocalDate, LocalTime> dailyAttendances;

    private Crew(String name) {
        this.name = name;
        this.dailyAttendances = new HashMap<>();
    }

    public static Crew createByName(String name) {
        return new Crew(name);
    }

    public void addDailyAttendance(Map<LocalDate, LocalTime> dateAndTime) {
        LocalDate date = TimeUtils.getDateFromDateAndTime(dateAndTime);

        validateIsNotAlreadyAttended(date);

        dailyAttendances.putAll(dateAndTime);
    }

    private void validateIsNotAlreadyAttended(LocalDate date) {
        if (dailyAttendances.containsKey(date)) {
            throw new IllegalArgumentException(ErrorCode.CHECK_ATTENDANCE_ALREADY_EXISTS.getFormat());
        }
    }

    public boolean hasName(String value) {
        return Objects.equals(name, value);
    }

    public String getName() {
        return name;
    }

    public void modifyDailyAttendance(Map<LocalDate, LocalTime> dateAndTime) {
        LocalDate date = TimeUtils.getDateFromDateAndTime(dateAndTime);
        validateDateAlreadyExists(date);

        dailyAttendances.putAll(dateAndTime);
    }

    public void validateDateAlreadyExists(LocalDate date) {
        if (!dailyAttendances.containsKey(date)) {
            throw new IllegalArgumentException(
                    ErrorCode.ATTENDANCE_RECORD_NOT_EXISTS_FORMAT.format(date.getDayOfMonth()));
        }
    }

    public List<AttendanceRecordResponse> getAttendanceRecords() {
        List<AttendanceRecordResponse> records = new ArrayList<>();
        for (int day = START_DAY_OF_DECEMBER; day <= END_DAY_OF_DECEMBER; day++) {
            if (!Calendar.checkIsWorkingDay(day)) {
                continue;
            }
            LocalTime time = dailyAttendances.get(LocalDate.now().withYear(2024).withMonth(12).withDayOfMonth(day));

            if (time == null) {
                records.add(new AttendanceRecordResponse(LocalDate.of(2024, 12, day), null,
                        AttendanceStatus.ABSENT));
                continue;
            }

            if (Calendar.isMonday(day)) {
                records.add(new AttendanceRecordResponse(LocalDate.of(2024, 12, day), time,
                        AttendanceStatus.getInMonday(time)));
            }
            if (!Calendar.isMonday(day)) {
                records.add(new AttendanceRecordResponse(LocalDate.of(2024, 12, day), time,
                        AttendanceStatus.getExceptMonday(time)));
            }
        }

        return records;
    }

    public LocalTime getTimeByDate(LocalDate date) {
        return dailyAttendances.get(date);
    }
}