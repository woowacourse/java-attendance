package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import attendance.exception.AttendanceArgumentException;

public record Attendances(Map<LocalDate, Attendance> attendances, SystemDateTime systemDateTime) {
    private static final String CANNOT_ATTENDANCE_WEEKEND_FORMAT = "MM월 dd일 E요일은 등교일이 아닙니다.";
    private static final String DUPLICATE_ATTENDANCE = "이미 출석하였습니다. 수정 기능을 이용해주세요.";
    private static final String CANT_FIND_ATTENDANCE = "출석 기록이 없습니다.";

    public Attendances(SystemDateTime systemDateTime) {
        this(new HashMap<>(), systemDateTime);
    }

    public void addAttendance(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        isDuplicateAttendance(date);
        isValidateSchedule(date);
        attendances.put(date, new Attendance(dateTime));
    }

    private void isDuplicateAttendance(LocalDate date) {
        Optional<Attendance> attendance = getOptionalAttendance(date);
        if (attendance.isPresent()) {
            throw new AttendanceArgumentException(DUPLICATE_ATTENDANCE);
        }
    }

    private void isValidateSchedule(LocalDate date) {
        if (!systemDateTime.isWorkingDay(date)) {
            var formatted = DateTimeFormatter.ofPattern(CANNOT_ATTENDANCE_WEEKEND_FORMAT).format(date);
            throw new AttendanceArgumentException(formatted);
        }
    }

    public Attendance getAttendance(LocalDate date) {
        Optional<Attendance> attendance = getOptionalAttendance(date);
        return attendance.orElseThrow(() ->
            new AttendanceArgumentException(CANT_FIND_ATTENDANCE)
        );
    }

    public Optional<Attendance> getOptionalAttendance(LocalDate date) {
        return Optional.ofNullable(attendances.get(date));
    }

    public void modifyAttendance(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        isValidateSchedule(date);
        attendances.put(date, new Attendance(dateTime));
    }

    public void updateStatics(StatusStatistics statusStatics) {
        statusStatics.update(attendances);
    }
}
