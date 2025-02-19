package attendance.repository;

import attendance.domain.Attendance;
import java.time.LocalDateTime;
import java.util.List;

public class AttendanceRepository {
    private final List<Attendance> attendances;

    public AttendanceRepository(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void add(Attendance currentAttendance) {
        for (Attendance attendance : attendances) {
            if (attendance.isAlreadyAttendance(currentAttendance)) {
                throw new IllegalArgumentException("[ERROR] 오늘은 이미 출석하셨습니다. 수정 기능을 이용해 주세요.");
            }
        }
        attendances.add(currentAttendance);
    }

    public List<Attendance> findAttendanceByName() {
        return List.of(new Attendance("체체", LocalDateTime.now()));
    }
}
