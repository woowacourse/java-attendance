package attendance.domain.attendance;

import static attendance.SystemDateConfig.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import attendance.SystemDateConfig;
import attendance.domain.AttendanceStatus;
import attendance.exception.AttendanceArgumentException;
import attendance.utility.DateTimeFormatterWrapper;

public class Attendances {
    public static final String DUPLICATE_DATE = "이미 출석되었습니다. 수정 기능을 이용해주세요.";

    private final List<Attendance> attendances = new ArrayList<>();
    private final List<String> attendanceHistory = new ArrayList<>();

    public void add(Attendance attendance) {
        attendances.add(attendance);
    }

    public Attendance findAttendance(Attendance attendance) {
        return attendances.stream()
            .filter(currendtAttendance -> currendtAttendance.equals(attendance))
            .findFirst()
            .orElseThrow(() -> new AttendanceArgumentException(Constant.CANT_FIND_ATTENDANCE));
    }

    public Optional<Attendance> findAttendance(LocalDate date) {
        return attendances.stream()
            .filter(attendance -> attendance.isEqualDate(date))
            .findFirst();
    }

    public void remove(Attendance attendance) {
        attendances.remove(attendance);
    }

    public boolean contains(Attendance attendance) {
        var date = attendance.dateTime().toLocalDate();
        return attendances.stream()
            .anyMatch(o -> o.dateTime().toLocalDate().equals(date));
    }

    public Map<AttendanceStatus, Integer> produceStatistic() {
        Map<AttendanceStatus, Integer> statistics = new HashMap<>();
        List<LocalDate> workingDays = extractWorkingDays();
        for (LocalDate workingDay : workingDays) {
            processAttendanceForDate(workingDay, statistics);
        }
        return statistics;
    }

    private List<LocalDate> extractWorkingDays() {
        return Stream.iterate(START_CALENDER, date -> date.plusDays(1))
            .limit(ChronoUnit.DAYS.between(START_CALENDER, getLastDay()))
            .filter(this::isValidWorkingDay)
            .collect(Collectors.toList());
    }

    private void processAttendanceForDate(LocalDate date, Map<AttendanceStatus, Integer> attendanceStatusMap) {
        try {
            Attendance attendance = findAttendance(date).orElseThrow(NullPointerException::new);
            updateAttendanceRecord(attendanceStatusMap, attendance);
        } catch (NullPointerException e) {
            handleAbsence(date, attendanceStatusMap);
        }
    }

    private boolean isValidWorkingDay(LocalDate date) {
        return !isWeekend(date) && !isHoliday(date);
    }

    private boolean isHoliday(LocalDate date) {
        return SystemDateConfig.DAT_OF_HOLIDAY.stream()
            .anyMatch(day -> date.getDayOfMonth() == day);
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek().getValue() >= DayOfWeek.SATURDAY.getValue();
    }

    public void validateDuplicate(Attendance attendance) {
        if (contains(attendance)) {
            throw new AttendanceArgumentException(DUPLICATE_DATE);
        }
    }

    private void updateAttendanceRecord(Map<AttendanceStatus, Integer> attendanceStatusMap, Attendance attendance) {
        var status = attendance.attendanceStatus();
        var dateTime = attendance.dateTime();

        String result = DateTimeFormatterWrapper.getFormatter(Constant.ATTENDANCE_FORMAT)
            .format(dateTime);
        String format = String.format(Constant.STATUS, status.getValue());
        attendanceHistory.add(result + format);
        putAttendanceState(attendanceStatusMap, status);
    }

    private void handleAbsence(LocalDate date, Map<AttendanceStatus, Integer> attendanceStatusMap) {
        String result = DateTimeFormatterWrapper.getFormatter(Constant.ABSENCE)
            .format(date);

        attendanceHistory.add(result);
        putAttendanceState(attendanceStatusMap, AttendanceStatus.ABSENCE);
    }

    private LocalDate getLastDay() {
        return SystemDateConfig.NOW_DATE.plusDays(1);
    }

    private void putAttendanceState(Map<AttendanceStatus, Integer> attendanceStatusMap, AttendanceStatus status) {
        attendanceStatusMap.put(status, attendanceStatusMap.getOrDefault(status, 0) + 1);
    }

    public List<String> getHistory() {
        return attendanceHistory;
    }

    private static final class Constant {
        private static final String CANT_FIND_ATTENDANCE = "출석 정보를 찾을 수 없습니다.";

        public static final String ATTENDANCE_FORMAT = "MM월 d일 E요일 HH:MM ";
        public static final String ABSENCE = "MM월 d일 E요일 --:-- (결석)\n";
        public static final String STATUS = "(%s)\n";

        private Constant() {
        }
    }
}
