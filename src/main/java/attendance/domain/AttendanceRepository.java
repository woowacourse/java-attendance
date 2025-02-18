package attendance.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AttendanceRepository {
    private final List<Attendance> attendances;

    public AttendanceRepository(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void add(final String name, final LocalDateTime localDateTime) {
        Optional<Attendance> crewAttendance = attendances.stream().filter(attendance -> attendance.isNameMatch(name))
                .findAny();
        if (crewAttendance.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 닉네임입니다.");
        }
        crewAttendance.get().add(localDateTime);
    }
}
