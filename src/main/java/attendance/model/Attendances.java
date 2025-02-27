package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendances {
    private final List<Attendance> attendances;

    public Attendances() {
        attendances = new ArrayList<>();
    }

    public void initAttendances(final String name, final List<List<String>> csvData) {
        Crew crew = new Crew(name);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m");
        csvData.stream()
                .filter(row -> row.getFirst().equals(name))
                .map(row -> new Attendance(
                        LocalDateTime.parse(row.getLast(), dateTimeFormatter)
                ))
                .forEach(attendances::add);

        calculateAttendancesType();
        fillEmptyAttendance();
    }

    private void calculateAttendancesType() {
        for (Attendance attendance : attendances) {
            attendance.calculateAttendanceType();
        }
    }

    private void fillEmptyAttendance() {
        LocalDate firstDate = LocalDate.of(2025, 2, 1);
        LocalDate today = LocalDate.now();
        for (LocalDate i = firstDate; i.isBefore(today); i = i.plusDays(1)) {
            if (isWorkday(i) && !isAttend(i)) {
                attendances.add(new Attendance(
                        LocalDateTime.of(i, LocalTime.of(0, 0)),
                        AttendanceType.ABSENT
                ));
            }
        }
    }

    private boolean isWorkday(final LocalDate date) {
        return date.getDayOfWeek() != DayOfWeek.SATURDAY
                && date.getDayOfWeek() != DayOfWeek.SUNDAY;
    }

    private boolean isAttend(final LocalDate date) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isSameDate(date));
    }

    public int calculatePresentCount() {
        return (int) attendances.stream()
                .filter(attendance -> attendance.getType() == AttendanceType.PRESENT)
                .count();
    }

    public int calculateLateCount() {
        return (int) attendances.stream()
                .filter(attendance -> attendance.getType() == AttendanceType.LATE)
                .count();
    }

    public int calculateAbsentCount() {
        return (int) attendances.stream()
                .filter(attendance -> attendance.getType() == AttendanceType.ABSENT)
                .count();
    }

    public void attend(final LocalDateTime dateTime) {
        attendances.add(new Attendance(dateTime, AttendanceType.of(dateTime)));
    }

    public Map<AttendanceType, Integer> getInfo() {
        Map<AttendanceType, Integer> attendanceInfo = new HashMap<>();
        attendanceInfo.put(AttendanceType.PRESENT, 0);
        attendanceInfo.put(AttendanceType.LATE, 0);
        attendanceInfo.put(AttendanceType.ABSENT, 0);

        for (Attendance attendance : attendances) {
            plusStatisticCount(attendance, AttendanceType.PRESENT, attendanceInfo);
            plusStatisticCount(attendance, AttendanceType.LATE, attendanceInfo);
            plusStatisticCount(attendance, AttendanceType.ABSENT, attendanceInfo);
        }
        return attendanceInfo;
    }

    private static void plusStatisticCount(Attendance attendance, AttendanceType present,
                                           Map<AttendanceType, Integer> attendanceInfo) {
        if (attendance.getType() == present) {
            attendanceInfo.put(present, attendanceInfo.get(present) + 1);
        }
    }

    public Attendance findAttendance(final LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameDate(date))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("출석 수정은 어제까지의 기록만 가능합니다."));  // 값이 없을 경우 null 반환
    }

    public void modifyAttendance(final LocalDateTime dateTime) {
        for (Attendance attendance : attendances) {
            if (attendance.isSameDate(dateTime.toLocalDate())) {
                attendance.modifyDateTime(dateTime);
                attendance.calculateAttendanceType();
            }
        }
    }

    public boolean hasTodayAttendance() {
        return attendances.stream().anyMatch(attendance -> attendance.isSameDate(LocalDate.now()));
    }

    public List<Attendance> getHistory() {
        return attendances.stream()
                .sorted(Comparator.comparingInt(attendance -> Integer.parseInt(attendance.getDateOfMonth()))).toList();
    }
}
