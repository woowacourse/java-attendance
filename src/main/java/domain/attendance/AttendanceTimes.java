package domain.attendance;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttendanceTimes {
    private final List<AttendanceTime> attendanceLog;

    private AttendanceTimes(List<AttendanceTime> attendanceLog) {
        this.attendanceLog = new ArrayList<>(attendanceLog);
    }

    public static AttendanceTimes of(List<AttendanceTime> attendanceTimes) {
        return new AttendanceTimes(attendanceTimes);
    }

    public boolean contains(LocalDate date) {
        return attendanceLog.stream()
                .anyMatch(attendanceTime -> attendanceTime.isSameDate(date));
    }

    public void addAttendance(AttendanceTime time) {
        boolean exist = attendanceLog.stream()
                .anyMatch(attendanceTime -> attendanceTime.isSameDate(time));
        if (exist) {
            throw new IllegalArgumentException("이미 출석 기록이 존재해 수정만 가능합니다.");
        }
        attendanceLog.add(time);
    }

    public List<AttendanceTime> readAttendance(LocalDate date) {
        return attendanceLog.stream()
                .filter(time -> time.isBefore(date))
                .toList();
    }

    public Optional<AttendanceTime> readAttendanceV2(LocalDate date) {
        return attendanceLog.stream()
                .filter(attendanceTime -> attendanceTime.isSameDate(date))
                .findAny();
    }

    public Optional<AttendanceTime> modifyAttendance(AttendanceTime time) {
        Optional<AttendanceTime> currentTime = attendanceLog.stream()
                .filter(attendanceTime -> attendanceTime.isSameDate(time))
                .findAny();
        Optional<AttendanceTime> previous = Optional.empty();
        if (currentTime.isEmpty()) {
            attendanceLog.add(time);
            return previous;
        }
        return Optional.of(currentTime.get().modify(time));
    }

    public int countAttendanceBeforeDate(LocalDate date) {
        return Math.toIntExact(
                attendanceLog.stream()
                        .filter(time -> time.isBefore(date) && !time.isAbsence() && !time.isLate())
                        .count()
        );
    }

    public int countLateBeforeDate(LocalDate date) {
        return Math.toIntExact(
                attendanceLog.stream()
                        .filter(time -> time.isBefore(date) && time.isLate())
                        .count()
        );
    }

    public int countAbsenceBeforeDate(LocalDate date) {
        return countWorkday(date) - (countAttendanceBeforeDate(date) + countLateBeforeDate(date));
    }

    private int countWorkday(LocalDate date) {
        int count = 0;
        for (int i = 1; i < date.getDayOfMonth(); i++) {
            LocalDate today = LocalDate.of(2024, 12, i);
            if (today.getDayOfWeek() == DayOfWeek.SATURDAY || today.getDayOfWeek() == DayOfWeek.SUNDAY) {
                continue;
            }
            count++;
        }
        return count;
    }
}
