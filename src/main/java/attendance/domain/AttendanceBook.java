package attendance.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record AttendanceBook(Map<String, Attendances> attendancesBook) {

    public AttendanceBook() {
        this(new HashMap<>());
    }

    public void put(String nickname, LocalDateTime dateTime) {
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
