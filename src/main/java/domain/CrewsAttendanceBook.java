package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CrewsAttendanceBook {
    private final Map<Crew, AttendanceBook> attendances;

    public CrewsAttendanceBook(Map<Crew, AttendanceBook> initialAttendances) {
        this.attendances = initialAttendances;
    }

    public Map<Crew, AttendanceBook> getAttendances() {
        return attendances;
    }

    public Crew getCrewByName(String name) {
        return attendances.keySet().stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하는 크루의 닉네임을 입력해주세요."));
    }

    public AttendanceBook getAttendanceBook(Crew crew) {
        return attendances.get(crew);
    }

    public Attendance checkIn(Crew crew, LocalDate localDate, LocalTime localTime) {
        AttendanceBook attendanceBook = attendances.get(crew);
        attendanceBook.validateWeekDay(localDate);
        attendanceBook.validateDuplicateCheckIn(localDate);

        Attendance attendance = new Attendance(localDate, localTime);
        attendanceBook.checkIn(attendance);
        return attendance;
    }

    public Attendance update(Crew crew, LocalDate localDate, LocalTime localTime) {
        AttendanceBook attendanceBook = attendances.get(crew);
        attendanceBook.validateWeekDay(localDate);
        attendanceBook.validateAfterToday(localDate);

        AttendanceBook newAttendanceBook = attendanceBook.update(localDate, localTime);

        attendances.put(crew, newAttendanceBook);
        return new Attendance(localDate, localTime);
    }

    public Set<PenaltyBook> calculatePenaltyBooks() {
        return attendances.entrySet().stream()
                .map(entry -> entry.getValue().createPenaltyBook(entry.getKey()))
                .collect(Collectors.toSet());
    }
}
