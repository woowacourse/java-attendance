package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CrewsAttendanceBook {
    private final Map<String, AttendanceBook> attendances;

    public CrewsAttendanceBook(Map<String, AttendanceBook> initialAttendances) {
        this.attendances = initialAttendances;
    }

    public Map<String, AttendanceBook> getAttendances() {
        return attendances;
    }

    public void checkIn(String name, LocalDate localDate, LocalTime localTime) {
        validateExistingCrew(name);

        AttendanceBook attendanceBook = attendances.get(name);
        attendanceBook.validateWeekDay(localDate);
        attendanceBook.validateDuplicateCheckIn(localDate);

        attendanceBook.checkIn(new Attendance(localDate, localTime));
    }

    public void update(String name, LocalDate localDate, LocalTime localTime) {
        validateExistingCrew(name);

        AttendanceBook attendanceBook = attendances.get(name);
        attendanceBook.validateWeekDay(localDate);
        attendanceBook.validateAfterToday(localDate);

        AttendanceBook newAttendanceBook = attendanceBook.update(localDate, localTime);

        attendances.put(name, newAttendanceBook);
    }


    private void validateExistingCrew(String name) {
        if (!attendances.containsKey(name)) {
            throw new IllegalArgumentException("존재하는 크루의 닉네임을 입력해주세요.");
        }
    }

    public Set<PenaltyBook> calculatePenaltyBooks() {
        return attendances.entrySet().stream()
                .map(entry -> entry.getValue().createPenaltyBook(entry.getKey()))
                .collect(Collectors.toSet());
    }
}
