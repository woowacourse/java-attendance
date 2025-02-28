package attendance.domain;

import java.util.List;
import java.util.Objects;

public class CrewAttendances {

    private final String nickname;
    private final List<Attendance> attendances;

    public CrewAttendances(String nickname, List<Attendance> attendances) {
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

    public AttendanceCount getAttendanceCount(AttendanceDate endAttendanceDate) {
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

        CrewAttendances that = (CrewAttendances) object;
        return Objects.equals(nickname, that.nickname) && Objects.equals(attendances, that.attendances);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(nickname);
        result = 31 * result + Objects.hashCode(attendances);
        return result;
    }
}
