package domain;

import static constants.NumberConstants.END_DAY_OF_DECEMBER;
import static constants.NumberConstants.START_DAY_OF_DECEMBER;

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
    private final String name;
    private final Map<LocalDate, LocalTime> dailyAttendances;

    private Crew(String name) {
        this.name = name;
        this.dailyAttendances = new HashMap<>();
    }

    public static Crew createByName(String name) {
        return new Crew(name);
    }

    public String getName() {
        return name;
    }

    public LocalTime getTimeByDate(LocalDate date) {
        return dailyAttendances.get(date);
    }

    public void validateRecordNotExists(LocalDate date) {
        if (!dailyAttendances.containsKey(date)) {
            throw new IllegalArgumentException(
                    ErrorCode.ATTENDANCE_RECORD_NOT_EXISTS_FORMAT.format(date.getDayOfMonth()));
        }
    }

    public void validateAttendanceAlreadyExists(LocalDate date) {
        if (dailyAttendances.containsKey(date)) {
            throw new IllegalArgumentException(ErrorCode.CHECK_ATTENDANCE_ALREADY_EXISTS.getFormat());
        }
    }

    public void addDailyAttendance(Map<LocalDate, LocalTime> dateAndTime) {
        LocalDate date = TimeUtils.getDateFromDateAndTime(dateAndTime);

        validateAttendanceAlreadyExists(date);

        dailyAttendances.putAll(dateAndTime);
    }

    public void modifyDailyAttendance(Map<LocalDate, LocalTime> dateAndTime) {
        LocalDate date = TimeUtils.getDateFromDateAndTime(dateAndTime);
        validateRecordNotExists(date);

        dailyAttendances.putAll(dateAndTime);
    }

    public boolean matchesName(String value) {
        return Objects.equals(name, value);
    }

    public List<AttendanceRecordResponse> getAttendanceRecords() {
        List<AttendanceRecordResponse> records = new ArrayList<>();
        for (int day = START_DAY_OF_DECEMBER; day <= END_DAY_OF_DECEMBER; day++) {
            if (!Calendar.checkIsWorkingDay(day)) {
                continue;
            }
            LocalTime time = getTimeByDate(LocalDate.of(2024, 12, day));

            if (time == null) { // 해당 날짜에 대한 기록이 없는 경우, 결석 처리 (현재는 2025년이므로)
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
}