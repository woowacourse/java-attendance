package domain.attendance;

import domain.date.AttendanceCustomDate;
import domain.date.Month;
import exception.DuplicateAttendanceException;
import java.util.stream.IntStream;
import service.dto.AttendanceHistoryResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class AttendanceBook {
    private static final int year = AttendanceCustomDate.YEAR;
    private static final Month month = AttendanceCustomDate.MONTH;

    private final Map<Integer, Attendance> attendances;

    public AttendanceBook() {
        this.attendances = new HashMap<>();
    }

    public Attendance create(int date, int hour, int minute) {
        if (attendances.containsKey(date)) {
            throw new DuplicateAttendanceException();
        }
        Attendance attendance = new Attendance(
                LocalDateTime.of(
                        year,
                        month.getValue(),
                        date,
                        hour,
                        minute
                )
        );
        attendances.put(date, attendance);
        return attendance;
    }

    public Optional<Attendance> findAttendanceByDate(int date) {
        if (attendances.containsKey(date)) {
            return Optional.of(attendances.get(date));
        }
        return Optional.empty();
    }

    public void replace(Attendance beforeAttendance, Attendance afterAttendance) {
        int date = beforeAttendance.getTime().getDayOfMonth();
        attendances.replace(date, beforeAttendance, afterAttendance);
    }

    //TODO : 인덴트 어떻게 줄이지...
    public List<AttendanceHistoryResponse> getAllAttendance(LocalDate limitDate) {
        List<AttendanceHistoryResponse> histories = new ArrayList<>();
        IntStream.range(1, limitDate.getDayOfMonth())
                .filter(date -> !month.isHoliday(date))
                .forEach(date -> {
                    if (attendances.containsKey(date)) {
                        Attendance attendance = attendances.get(date);
                        histories.add(new AttendanceHistoryResponse(
                                attendance.getTime().toLocalDate(),
                                Optional.of(attendance.getTime().toLocalTime()),
                                attendance.getStatus())
                        );
                    }
                    else {
                        histories.add(new AttendanceHistoryResponse(
                                LocalDate.of(year, month.getValue(), date),
                                Optional.empty(),
                                AttendanceStatus.ABSENCE)
                        );
                    }
                });
        return histories;
    }

    public Map<AttendanceStatus, Integer> calculateAttendanceResult(LocalDate limitDate) {
        Map<AttendanceStatus, Integer> result = new HashMap<>();
        result.put(AttendanceStatus.ATTENDANCE, 0);
        result.put(AttendanceStatus.LATE, 0);
        result.put(AttendanceStatus.ABSENCE, 0);
        IntStream.range(1, limitDate.getDayOfMonth())
                .filter(date -> !month.isHoliday(date))
                .forEach(date -> {
                    if (attendances.containsKey(date)) {
                        Attendance attendance = attendances.get(date);
                        AttendanceStatus status = attendance.getStatus();
                        result.replace(status, result.get(status) + 1);
                    }
                    else {
                        result.replace(AttendanceStatus.ABSENCE, result.get(AttendanceStatus.ABSENCE) + 1);
                    }
        });
        return result;
    }

    public int getLateCountAt(LocalDate limitDate) {
        return (int) IntStream.range(1, limitDate.getDayOfMonth())
                .filter(date -> !month.isHoliday(date))
                .filter(attendances::containsKey)
                .filter(date -> attendances.get(date).getStatus().equals(AttendanceStatus.LATE))
                .count();
    }

    public int getAbsenceCountAt(LocalDate limitDate) {
        return (int) IntStream.range(1, limitDate.getDayOfMonth())
                .filter(date -> !month.isHoliday(date))
                .filter(date -> !attendances.containsKey(date)
                        || attendances.get(date).getStatus().equals(AttendanceStatus.ABSENCE))
                .count();
    }
}
