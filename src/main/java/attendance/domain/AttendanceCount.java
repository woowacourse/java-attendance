package attendance.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class AttendanceCount {

    private static final LocalDate ATTENDANCE_START_DATE = LocalDate.of(2024, 12, 1);

    private final Map<AttendanceStatus, Integer> attendances;

    public AttendanceCount(Map<AttendanceStatus, Integer> attendances) {
        this.attendances = attendances;
    }

    public static AttendanceCount create(String nickname, List<Attendance> attendances, LocalDate AttendanceEndDate) {
        Map<AttendanceStatus, Integer> attendanceMap = new HashMap<>();
        LocalDate currentDate = ATTENDANCE_START_DATE;
        while (currentDate.isBefore(AttendanceEndDate) || currentDate.isEqual(AttendanceEndDate)) {
            if (!Attendance.canAttend(currentDate)) {
                System.out.println("date = " + currentDate);
                currentDate = currentDate.plusDays(1);
                continue;
            }
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
            currentDate = currentDate.plusDays(1);
        }
        return new AttendanceCount(attendanceMap);
    }

    private static Optional<Attendance> findAttendanceByDate(String nickname,
                                                             List<Attendance> attendances,
                                                             LocalDate attendanceDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isAlreadyAttend(nickname, attendanceDate))
                .findFirst();
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
