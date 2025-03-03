package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import attendance.exception.AttendanceArgumentException;
import attendance.interfaces.SystemDateTime;

public record Attendances(Map<LocalDate, Attendance> attendances, SystemDateTime systemDateTime) {
    private static final String CANNOT_ATTENDANCE_WEEKEND_FORMAT = "MM월 dd일 E요일은 등교일이 아닙니다.";
    private static final String CANNOT_FIND_ATTENDANCE = "출석 기록이 없습니다.";
    private static final String DUPLICATE_ATTENDANCE = "이미 출석하였습니다. 수정 기능을 이용해주세요.";

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
        if (attendance.isEmpty() || attendance.get().isTruancy()) {
            throw new AttendanceArgumentException(CANNOT_FIND_ATTENDANCE);
        }
        return attendance.get();
    }

    public Optional<Attendance> getOptionalAttendance(LocalDate date) {
        return Optional.ofNullable(attendances.get(date));
    }

    public void modifyAttendance(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        isValidateSchedule(date);
        attendances.put(date, new Attendance(dateTime));
    }

    public List<Attendance> getAttendancesWithTruancy() {
        List<LocalDate> workingDates = systemDateTime.extractWorkingDays();
        List<Attendance> attendanceHistory = new ArrayList<>();

        for (LocalDate date : workingDates) {
            var attendance = attendances.getOrDefault(date, Attendance.generateTruancy(date));
            attendanceHistory.add(attendance);
        }

        return attendanceHistory;
    }

    public Map<LocalDate, AttendanceStatus> updateStatics() {
        List<LocalDate> workingDates = systemDateTime.extractWorkingDays();
        Map<LocalDate, AttendanceStatus> attendanceStatus = new HashMap<>();
        for (LocalDate date : workingDates) {
            attendanceStatus.put(date, getOrAbsence(date));
        }
        return attendanceStatus;
    }

    private AttendanceStatus getOrAbsence(LocalDate date) {
        Optional<Attendance> optionalAttendance = getOptionalAttendance(date);
        return optionalAttendance
            .map(Attendance::status)
            .orElse(AttendanceStatus.ABSENCE);
    }
}
