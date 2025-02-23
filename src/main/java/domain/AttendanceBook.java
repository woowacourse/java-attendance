package domain;

import exception.AttendanceNotExistException;
import exception.DuplicateAttendanceException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class AttendanceBook {
    private final HashSet<Attendance> attendances; // key: 몇 일, value: 출석 시간

    public AttendanceBook(int year, int month) {
        Month customMonth = Month.of(month);

        attendances = new HashSet<>();
        for (int day = 1; day <= customMonth.getLastDay(); day++) {
            if (!customMonth.isHoliday(day)) {
                LocalDate date = LocalDate.of(year, month, day);
                attendances.add(Attendance.empty(date));
            }
        }
    }

    public Attendance create(LocalDate date, LocalTime time) {
        final boolean isExist = attendances.stream()
                .anyMatch(attendance -> attendance.getDate().isEqual(date) && !attendance.isEmpty());
        if (isExist) {
            throw new DuplicateAttendanceException();
        }
        Attendance attendance = Attendance.of(date, time);
        addAttendance(attendance);
        return attendance;
    }

    public Attendance findAttendanceByDate(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.getDate().isEqual(date))
                .findFirst()
                .orElse(Attendance.empty(date));
    }

    public void replace(LocalDate date, LocalTime time) {
        Attendance oldAttendance = attendances.stream()
                .filter(attendance -> attendance.getDate().isEqual(date))
                .findFirst()
                .orElseThrow(AttendanceNotExistException::new);
        Attendance modifiedAttendance = oldAttendance.modify(time);
        addAttendance(modifiedAttendance);
    }

    public List<Attendance> getAllAttendances(final int limitDay) {
        return attendances.stream()
                .sorted(Comparator.comparing(Attendance::getDate))
                .filter(attendance -> attendance.getDate().getDayOfMonth() < limitDay)
                .toList();
    }

    public Map<AttendanceStatus, Integer> calculateAttendanceResult(final int limitDay) {
        Map<AttendanceStatus, Integer> result = new HashMap<>();
        result.put(AttendanceStatus.ATTENDANCE, 0);
        result.put(AttendanceStatus.LATE, 0);
        result.put(AttendanceStatus.ABSENCE, 0);

        List<Attendance> attendances = getAllAttendances(limitDay);
        for (Attendance attendance : attendances) {
            AttendanceStatus status = attendance.getStatus();
            if (status == AttendanceStatus.TRUANCY) {
                status = AttendanceStatus.ABSENCE;
            }
            result.replace(status, result.get(status) + 1);
        }
        return result;
    }

    public int getLateCount(final int limitDay) {
        List<Attendance> attendances = getAllAttendances(limitDay);
        int count = 0;
        for (Attendance attendance : attendances) {
            if (attendance.getStatus() == AttendanceStatus.LATE) {
                count++;
            }
        }
        return count;
    }

    public int getAbsenceCount(final int limitDay) {
        List<Attendance> attendances = getAllAttendances(limitDay);
        int count = 0;
        for (Attendance attendance : attendances) {
            if (attendance.getStatus() == AttendanceStatus.ABSENCE
                    || attendance.getStatus() == AttendanceStatus.TRUANCY) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AttendanceBook that = (AttendanceBook) object;
        return attendances.containsAll(that.attendances);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendances);
    }

    private void addAttendance(Attendance attendance) {
        if (!attendances.add(attendance)) {
            attendances.remove(attendance);
            attendances.add(attendance);
        }
    }
}
