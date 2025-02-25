package domain;

import exception.DuplicateAttendanceException;
import exception.InvalidDateException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class AttendanceBook {
    private final HashSet<Attendance> attendances;

    public AttendanceBook() {
        attendances = new HashSet<>();
    }

    public Attendance create(LocalDate date, LocalTime time) {
        final boolean isExist = attendances.stream().anyMatch(attendance -> attendance.isAttendedOn(date));
        if (isExist) {
            throw new DuplicateAttendanceException();
        }
        if (isHoliday(date)) {
            throw new InvalidDateException();
        }
        Attendance attendance = Attendance.of(date, time);
        addAttendance(attendance);
        return attendance;
    }

    public Attendance findAttendanceByDate(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isAttendedOn(date))
                .findFirst()
                .orElse(Attendance.empty(date));
    }

    public Attendance replace(LocalDate date, LocalTime time) {
        Attendance oldAttendance = attendances.stream()
                .filter(attendance -> attendance.isAttendedOn(date))
                .findFirst()
                .orElse(Attendance.empty(date));
        Attendance modifiedAttendance = oldAttendance.modify(time);
        addAttendance(modifiedAttendance);
        return modifiedAttendance;
    }

    // TODO: 출석 내역을 날짜 기준으로 정렬해서 보여주는 것은 뷰의 책임으로 넘기기
    public List<Attendance> getAllAttendances(LocalDate startDate, LocalDate endDate) {
        List<Attendance> result = new ArrayList<>();
        for (LocalDate current = startDate; current.isBefore(endDate); current = current.plusDays(1)) {
            if (isHoliday(current)) {
                continue;
            }
            Attendance attendance = findAttendanceByDate(current);
            result.add(attendance);
        }
        return result;
    }

    public Map<AttendanceStatus, Integer> calculateAttendanceResult(LocalDate startDate, LocalDate endDate) {
        Map<AttendanceStatus, Integer> result = new HashMap<>();
        result.put(AttendanceStatus.ATTENDANCE, 0);
        result.put(AttendanceStatus.LATE, 0);
        result.put(AttendanceStatus.ABSENCE, 0);

        for (LocalDate current = startDate; current.isBefore(endDate); current = current.plusDays(1)) {
            if (isHoliday(current)) {
                continue;
            }
            Attendance attendance = findAttendanceByDate(current);
            AttendanceStatus status = attendance.getStatus();
            if (attendance.isAbsence()) {
                status = AttendanceStatus.ABSENCE;
            }
            result.replace(status, result.get(status) + 1);
        }

        return result;
    }

    public int getLateCount(LocalDate startDate, LocalDate endDate) {
        int count = 0;
        for (LocalDate current = startDate; current.isBefore(endDate); current = current.plusDays(1)) {
            if (isHoliday(current)) {
                continue;
            }
            Attendance attendance = findAttendanceByDate(current);
            if (attendance.getStatus() == AttendanceStatus.LATE) {
                count++;
            }
        }
        return count;
    }

    public int getAbsenceCount(LocalDate startDate, LocalDate endDate) {
        int count = 0;
        for (LocalDate current = startDate; current.isBefore(endDate); current = current.plusDays(1)) {
            if (isHoliday(current)) {
                continue;
            }
            Attendance attendance = findAttendanceByDate(current);
            if (attendance.getStatus() == AttendanceStatus.ABSENCE || attendance.isAbsence()) {
                count++;
            }
        }
        return count;
    }

    private void addAttendance(Attendance attendance) {
        if (!attendances.add(attendance)) {
            attendances.remove(attendance);
            attendances.add(attendance);
        }
    }

    private boolean isHoliday(LocalDate date) {
        Month month = Month.of(date.getMonthValue());
        return month.isHoliday(date.getDayOfMonth());
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
}
