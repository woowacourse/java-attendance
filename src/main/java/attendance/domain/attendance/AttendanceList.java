package attendance.domain.attendance;

import static attendance.common.SystemDateConfig.*;

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

import attendance.common.SystemDateConfig;
import attendance.common.exception.AttendanceArgumentException;
import attendance.common.utill.DateTimeFormatterWrapper;
import attendance.domain.AttendanceStatus;

public class AttendanceList {
    private final List<Attendance> attendances = new ArrayList<>();
    private final List<String> attendanceHistory = new ArrayList<>();

    public void add(Attendance attendance) {
        attendances.add(attendance);
    }

    public Attendance findAttendance(Attendance attendance) {
        return attendances.stream()
            .filter((i) -> i.equals(attendance))
            .findFirst()
            .orElseThrow(() -> new AttendanceArgumentException(Constant.CANT_FIND_INFO));
    }

    public Optional<Attendance> findAttendance(LocalDate date) {
        return attendances.stream()
            .filter((i) -> i.isEqualDate(date))
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
        workingDays.forEach(date -> processAttendanceForDate(date, statistics));
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

    private void updateAttendanceRecord(Map<AttendanceStatus, Integer> attendanceStatusMap, Attendance attendance) {
        var status = attendance.attendanceStatus();
        var dateTime = attendance.dateTime();

        String result = DateTimeFormatterWrapper.getFormatter(Constant.ATTENDANCE_INFO)
            .format(dateTime);
        String format = String.format(Constant.STATUS, status.getStatus());
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
        return SystemDateConfig.SYSTEM_TODAY.plusDays(1);
    }

    private void putAttendanceState(Map<AttendanceStatus, Integer> attendanceStatusMap, AttendanceStatus status) {
        attendanceStatusMap.put(status, attendanceStatusMap.getOrDefault(status, 0) + 1);
    }

    public List<String> getHistory() {
        return attendanceHistory;
    }

    private static final class Constant {
        private static final String CANT_FIND_INFO = "출석 정보를 찾을 수 없습니다.";

        public static final String ATTENDANCE_INFO = "MM월 d일 E요일 HH:MM ";
        public static final String ABSENCE = "MM월 d일 E요일 --:-- (결석)\n";
        public static final String STATUS = "(%s)\n";

        private Constant() {
        }
    }
}
