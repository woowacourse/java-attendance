package attendance.domain;

import attendance.domain.dto.ModifyAttendanceResult;
import java.time.LocalDateTime;
import java.util.List;

public class MemberAttendance {
    private final Crew crew;
    private final List<Attendance> attendances;

    public MemberAttendance(Crew crew, List<Attendance> attendances) {
        this.crew = crew;
        this.attendances = attendances;
    }

    public Crew getCrew() {
        return crew;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public List<Attendance> modifyAttendanceRecord(LocalDateTime attendanceDateTime) {
        Attendance modifyOldAttendance = attendances.stream()
                .filter(attendance -> attendance.getAttendanceDate().equals(attendanceDateTime.toLocalDate()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 날입니다."));

        attendances.remove(modifyOldAttendance);
        Attendance modifyNewAttendance = new Attendance(attendanceDateTime);
        attendances.add(modifyNewAttendance);

        return List.of(modifyOldAttendance, modifyNewAttendance);
    }
}
