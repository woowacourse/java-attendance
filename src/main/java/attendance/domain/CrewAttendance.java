package attendance.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class CrewAttendance {

    private final String nickname;
    private final List<Attendance> attendances;

    public CrewAttendance(String nickname, Attendance... attendances) {
        this(nickname, new ArrayList<>(List.of(attendances)));
    }

    public CrewAttendance(String nickname, List<Attendance> attendances) {
        validateAttendances(nickname, attendances);
        this.nickname = nickname;
        this.attendances = attendances;
    }

    private void validateAttendances(String nickname, List<Attendance> attendances) {
        if (!isAllAttendancesMatchNickname(nickname, attendances)) {
            throw new IllegalArgumentException(nickname + "의 출석만 이용하여 생성가능합니다.");
        }
    }

    private boolean isAllAttendancesMatchNickname(String nickname, List<Attendance> attendances) {
        return attendances.stream()
                .allMatch(attendance -> attendance.isEqualNickname(nickname));
    }

    public AttendanceResult createAttendanceResult(LocalDate endAttendanceDate) {
        Map<AttendanceStatus, Integer> attendanceMap = new HashMap<>();
        AttendanceDate currentDate = AttendanceDate.ATTENDANCE_START_DATE;
        while (currentDate.isBeforeAndEqual(endAttendanceDate)) {
            findAttendanceByDate(nickname, attendances, currentDate)
                    .ifPresentOrElse(
                            attendance -> putAttendance(attendance, attendanceMap),
                            () -> putAbsentAttendance(attendanceMap)
                    );
            currentDate = currentDate.nextDate();
        }
        return new AttendanceResult(nickname, attendanceMap);
    }

    private Optional<Attendance> findAttendanceByDate(String nickname,
                                                      List<Attendance> attendances,
                                                      AttendanceDate attendanceDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isAlreadyAttend(nickname, attendanceDate))
                .findFirst();
    }

    private void putAttendance(Attendance attendance, Map<AttendanceStatus, Integer> attendanceMap) {
        attendanceMap.put(
                attendance.getAttendanceStatus(),
                attendanceMap.getOrDefault(attendance.getAttendanceStatus(), 0) + 1
        );
    }

    private void putAbsentAttendance(Map<AttendanceStatus, Integer> attendanceMap) {
        attendanceMap.put(
                AttendanceStatus.ABSENT,
                attendanceMap.getOrDefault(AttendanceStatus.ABSENT, 0) + 1
        );
    }

    public List<Attendance> getAttendances() {
        return Collections.unmodifiableList(attendances);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        CrewAttendance that = (CrewAttendance) object;
        return Objects.equals(nickname, that.nickname) && Objects.equals(attendances, that.attendances);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(nickname);
        result = 31 * result + Objects.hashCode(attendances);
        return result;
    }
}
