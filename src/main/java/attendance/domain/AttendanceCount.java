package attendance.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class AttendanceCount {

    private static final AttendanceDate ATTENDANCE_START_DATE = new AttendanceDate(LocalDate.of(2024, 12, 2));

    private final Map<AttendanceStatus, Integer> attendances;

    public AttendanceCount(Map<AttendanceStatus, Integer> attendances) {
        this.attendances = attendances;
    }

    public static AttendanceCount create(String nickname,
                                         List<Attendance> attendances,
                                         AttendanceDate attendanceEndDate) {
        Map<AttendanceStatus, Integer> attendanceMap = new HashMap<>();
        AttendanceDate currentDate = ATTENDANCE_START_DATE;
        while (currentDate.isBeforeAndEqual(attendanceEndDate)) {
            findAttendanceByDate(nickname, attendances, currentDate)
                    .ifPresentOrElse(
                            attendance -> attendanceMap.put(
                                    attendance.getAttendanceStatus(),
                                    attendanceMap.getOrDefault(attendance.getAttendanceStatus(), 0) + 1
                            ),
                            () -> attendanceMap.put(
                                    AttendanceStatus.ABSENT,
                                    attendanceMap.getOrDefault(AttendanceStatus.ABSENT, 0) + 1
                            )
                    );
            currentDate = currentDate.nextDate();
        }
        return new AttendanceCount(attendanceMap);
    }

    private static Optional<Attendance> findAttendanceByDate(String nickname,
                                                             List<Attendance> attendances,
                                                             AttendanceDate attendanceDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isAlreadyAttend(nickname, attendanceDate))
                .findFirst();
    }

    public WarningLevel getWarningLevel() {
        return null;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        AttendanceCount that = (AttendanceCount) object;
        return Objects.equals(attendances, that.attendances);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendances);
    }
}
