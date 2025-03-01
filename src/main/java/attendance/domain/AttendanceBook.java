package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import attendance.exception.AttendanceArgumentException;

public record AttendanceBook(Map<Nickname, Attendances> attendancesBook) {

    public AttendanceBook() {
        this(new HashMap<>());
    }

    public void put(String name, LocalDateTime dateTime) {
        var nickname = new Nickname(name);
        Optional<Attendances> attendances = Optional.ofNullable(attendancesBook.get(nickname));
        if (attendances.isPresent()) {
            attendances.get().put(dateTime);
        } else {
            var newAttendances = new Attendances();
            newAttendances.put(dateTime);
            attendancesBook.put(nickname, newAttendances);
        }
    }

    public void add(Nickname nickname, LocalDateTime dateTime) {
        Attendances attendances = Optional.ofNullable(attendancesBook.get(nickname))
            .orElseThrow(() -> new AttendanceArgumentException("등록되지 않은 닉네임입니다."));
        attendances.add(dateTime);
    }

    public Attendance getAttendance(Nickname nickname, LocalDate date) {
        var attendances = attendancesBook.get(nickname);
        return attendances.get(date);
    }
}
