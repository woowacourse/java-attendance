package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.DayOfWeek.*;

public class Attendances {

    public static final int START_DAY_OF_MONTH = 1;
    private final List<Attendance> attendances = new ArrayList<>();

    public Attendances(final List<LocalDateTime> attendanceDateTimes, final LocalDateTime today) {
        for (int day = START_DAY_OF_MONTH; day < today.getDayOfMonth(); day++) {
            LocalDate localDate = LocalDate.of(today.getYear(), today.getMonth(), day);
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            if (dayOfWeek.equals(SUNDAY) || dayOfWeek.equals(SATURDAY) || Holiday.isExists(localDate)) {
                continue;
            }
            this.attendances.add(initializeAttendance(attendanceDateTimes, today, day));
        }
    }

    private Attendance initializeAttendance(final List<LocalDateTime> attendanceDateTimes, final LocalDateTime today, final int day) {
        LocalDate targetDate = LocalDate.of(today.getYear(), today.getMonth(), day);
        return attendanceDateTimes.stream()
                .filter(dateTime -> dateTime.toLocalDate()
                        .isEqual(targetDate))
                .findAny()
                .map(dateTime -> new Attendance(
                        new AttendanceDate(dateTime.toLocalDate()), new AttendanceTime(dateTime.toLocalTime())))
                .orElse(Attendance.makeAbsence(targetDate));
    }

    public void addAttendance(final Attendance attendance) {
        attendances.add(attendance);
    }

    public Attendance findAttendanceByLocalDate(final LocalDate findDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameDate(findDate))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("출석 기록이 존재하지 않습니다."));
    }

    public List<Attendance> findAllBeforeToday(final LocalDateTime today) {
        boolean hasTodayAttendance = attendances.stream()
                .anyMatch(attendance -> attendance.isSameDate(today.toLocalDate()));
        if (hasTodayAttendance) {
            return attendances.subList(0, attendances.size());
        }
        return attendances.stream()
                .toList();
    }

    public boolean existsByLocalDate(final LocalDate localDate) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isSameDate(localDate));
    }

    public void remove(Attendance attendance) {
        this.attendances.remove(attendance);
    }

    public Map<String, Integer> calculateStatusCount() {
        List<AttendanceStatus> statuses = attendances.stream()
                .map(Attendance::calculateStatus)
                .toList();
        return statuses.stream()
                .collect(Collectors.groupingBy(AttendanceStatus::getText,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue))
                );
    }

    public ExpulsionStatus calculateExpulsionStatus() {
        List<AttendanceStatus> statuses = attendances.stream()
                .map(Attendance::calculateStatus)
                .toList();
        int totalAbsentCount = AttendanceStatus.calculateTotalAbsentCount(statuses);
        return ExpulsionStatus.findByAbsentCount(totalAbsentCount);
    }

    public List<Attendance> getAttendances() {
        return attendances.stream()
                .toList();
    }

}
