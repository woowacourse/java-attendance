package domain.attendance;

import domain.date.CustomDate;
import domain.date.CustomMonth;
import exception.sub.DuplicateAttendanceException;
import java.util.stream.IntStream;
import service.dto.AttendanceHistoryResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class AttendanceBook {
    private static final int year = CustomDate.YEAR;
    private static final CustomMonth CUSTOM_MONTH = CustomDate.CUSTOM_MONTH;

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
                        CUSTOM_MONTH.getValue(),
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

    public List<AttendanceHistoryResponse> getAllAttendanceUntilBefore(LocalDate limitDate) {
        List<AttendanceHistoryResponse> histories = new ArrayList<>();

        extractDatesExceptHolidayUntil(limitDate).forEach(date -> {
            if (attendances.containsKey(date)) {
                Attendance attendance = attendances.get(date);
                histories.add(new AttendanceHistoryResponse(
                        attendance.getTime().toLocalDate(),
                        Optional.of(attendance.getTime().toLocalTime()),
                        attendance.getStatus())
                );
                return;
            }
            histories.add(new AttendanceHistoryResponse(
                    LocalDate.of(year, CUSTOM_MONTH.getValue(), date),
                    Optional.empty(),
                    AttendanceStatus.ABSENCE)
            );
        });
        return histories;
    }

    public Map<AttendanceStatus, Integer> findAttendanceResultUntilBefore(LocalDate limitDate) {
        Map<AttendanceStatus, Integer> result = new HashMap<>(Map.of(
                AttendanceStatus.ATTENDANCE, 0,
                AttendanceStatus.LATE, 0,
                AttendanceStatus.ABSENCE, 0
        ));
        extractDatesExceptHolidayUntil(limitDate).forEach(date -> {
            if (attendances.containsKey(date)) {
                Attendance attendance = attendances.get(date);
                AttendanceStatus status = attendance.getStatus();
                result.replace(status, result.get(status) + 1);
                return;
            }
            result.replace(AttendanceStatus.ABSENCE, result.get(AttendanceStatus.ABSENCE) + 1);
        });
        return result;
    }

    public int getLateCountUntilBefore(LocalDate limitDate) {
        return (int) extractDatesExceptHolidayUntil(limitDate).stream()
                .filter(attendances::containsKey)
                .filter(date -> attendances.get(date).getStatus().equals(AttendanceStatus.LATE))
                .count();
    }

    public int getAbsenceCountUntilBefore(LocalDate limitDate) {
        return (int) extractDatesExceptHolidayUntil(limitDate).stream()
                .filter(date -> !attendances.containsKey(date)
                        || attendances.get(date).getStatus().equals(AttendanceStatus.ABSENCE))
                .count();
    }

    private List<Integer> extractDatesExceptHolidayUntil(LocalDate limitDate) {
        return IntStream.range(1, limitDate.getDayOfMonth())
                .filter(date -> !CUSTOM_MONTH.isHolidayAt(date))
                .boxed().toList();
    }
}
