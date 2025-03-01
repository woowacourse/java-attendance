package attendance.domain;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class AttendanceResult implements Comparable<AttendanceResult> {

    private static final int LATE_TO_ABSENT_UNIT = 3;

    private final String nickname;
    private final Map<AttendanceStatus, Integer> attendanceStatus;

    public AttendanceResult(String nickname, Map<AttendanceStatus, Integer> attendanceStatus) {
        this.nickname = nickname;
        this.attendanceStatus = attendanceStatus;
    }

    public static AttendanceResult create(String nickname,
                                          List<Attendance> attendances,
                                          LocalDate attendanceEndDate) {
        Map<AttendanceStatus, Integer> attendanceMap = new HashMap<>();
        AttendanceDate currentDate = AttendanceDate.ATTENDANCE_START_DATE;
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
        return new AttendanceResult(nickname, attendanceMap);
    }

    private static Optional<Attendance> findAttendanceByDate(String nickname,
                                                             List<Attendance> attendances,
                                                             AttendanceDate attendanceDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isAlreadyAttend(nickname, attendanceDate))
                .findFirst();
    }

    public WarningLevel getWarningLevel() {
        return WarningLevel.from(calculateTotalAbsent());
    }

    private int calculateTotalAbsent() {
        int lateCount = getLateCount();
        int absentCount = getAbsentCount();
        return lateCount / LATE_TO_ABSENT_UNIT + absentCount;
    }

    public String getNickname() {
        return nickname;
    }

    public Map<AttendanceStatus, Integer> getAttendanceStatus() {
        return Collections.unmodifiableMap(attendanceStatus);
    }

    private int getLateCount() {
        return attendanceStatus.getOrDefault(AttendanceStatus.LATE, 0);
    }

    private int getAbsentCount() {
        return attendanceStatus.getOrDefault(AttendanceStatus.ABSENT, 0);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        AttendanceResult that = (AttendanceResult) object;
        return Objects.equals(attendanceStatus, that.attendanceStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendanceStatus);
    }

    @Override
    public int compareTo(AttendanceResult o) {
        if (this.calculateTotalAbsent() == o.calculateTotalAbsent()) {
            if (this.getLateCount() == o.getLateCount()) {
                return this.nickname.compareTo(o.nickname);
            }
            return o.getLateCount() - this.getLateCount();
        }
        return o.calculateTotalAbsent() - this.calculateTotalAbsent();
    }
}
