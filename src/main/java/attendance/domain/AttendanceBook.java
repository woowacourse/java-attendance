package attendance.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record AttendanceBook(Map<Nickname, Attendances> attendancesBook) {

    public AttendanceBook() {
        this(new HashMap<>());
    }

    public void put(String name, LocalDateTime dateTime) {
        var nickname = new Nickname(name);
        Optional<Attendances> attendances = Optional.ofNullable(attendancesBook.get(nickname));
        if (attendances.isPresent()) {
            attendances.get().add(dateTime);
        } else {
            var newAttendances = new Attendances();
            newAttendances.add(dateTime);
            attendancesBook.put(nickname, newAttendances);
        }
    }
}
