package domain;

import constants.DateConstants;
import exception.DuplicateAttendanceException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class AttendanceBook {
    private final int year;
    private final Month month;
    private final Map<Integer, Optional<Attendance>> attendances; // key: 몇 일, value: 출석 시간

    public AttendanceBook(int year, int month) {
        this.year = year;
        this.month = Month.of(month);

        attendances = new HashMap<>();
        for (int day = 1; day <= this.month.getLastDay(); day++) {
            attendances.put(day, Optional.empty());
        }
    }

    public Attendance create(int date, int hour, int minute) {
        if (attendances.get(date).isPresent()) {
            throw new DuplicateAttendanceException();
        }
        Attendance attendance = new Attendance(
                LocalDateTime.of(DateConstants.YEAR, DateConstants.MONTH.getValue(), date, hour, minute)
        );
        attendances.put(date, Optional.of(attendance));
        return attendance;
    }

    public Optional<Attendance> findAttendanceByDate(int date) {
        if (attendances.containsKey(date)) {
            return attendances.get(date);
        }
        return Optional.empty();
    }

    public void replace(Attendance beforeAttendance, Attendance afterAttendance) {
        int date = beforeAttendance.getTime().getDayOfMonth();
        attendances.replace(date, Optional.of(beforeAttendance), Optional.of(afterAttendance));
    }

    public List<AttendanceHistory> getAllAttendanceHistory(final int limitDay) {
        List<AttendanceHistory> histories = new ArrayList<>();

        for (int day = 1; day < limitDay; day++) {
            if (!month.isHoliday(day)) {
                AttendanceHistory attendanceHistory = AttendanceHistory.of(
                        LocalDate.of(year, month.getValue(), day), attendances.get(day)
                );
                histories.add(attendanceHistory);
            }
        }
        return histories;
    }

    public Map<AttendanceStatus, Integer> calculateAttendanceResult(final int limitDay) {
        Map<AttendanceStatus, Integer> result = new HashMap<>();
        result.put(AttendanceStatus.ATTENDANCE, 0);
        result.put(AttendanceStatus.LATE, 0);
        result.put(AttendanceStatus.ABSENCE, 0);
        for (int date = 1; date < limitDay; date++) {
            if (!month.isHoliday(date)) {
                attendances.get(date).ifPresentOrElse(attendance -> {
                    AttendanceStatus status = attendance.getStatus();
                    result.replace(status, result.get(status) + 1);
                }, () -> result.replace(AttendanceStatus.ABSENCE, result.get(AttendanceStatus.ABSENCE) + 1));
            }
        }
        return result;
    }

    public int getLateCount(final int limitDay) {
        int count = 0;
        for (int day = 1; day < limitDay; day++) {
            if (!month.isHoliday(day)
                    && attendances.get(day).isPresent()
                    && attendances.get(day).get().getStatus() == AttendanceStatus.LATE
            ) {
                count++;
            }
        }
        return count;
    }

    public int getAbsenceCount(final int limitDay) {
        int count = 0;
        for (int day = 1; day < limitDay; day++) {
            Optional<Attendance> attendance = attendances.get(day);
            if (!month.isHoliday(day) && (
                    attendance.isEmpty() || attendance.get().getStatus() == AttendanceStatus.ABSENCE
            )) {
                count++;
            }
        }
        return count;
    }
}
