package attendance.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CrewAttendance {

    private final String nickname;
    private final List<Attendance> attendances;

    public CrewAttendance(String nickname, Attendance... attendances) {
        validateAttendances(nickname, attendances);
        this.nickname = nickname;
        this.attendances = new ArrayList<>(List.of(attendances));
    }

    public CrewAttendance(String nickname, List<Attendance> attendances) {
        validateAttendances(nickname, attendances.toArray(Attendance[]::new));
        this.nickname = nickname;
        this.attendances = attendances;
    }

    private void validateAttendances(String nickname, Attendance... attendances) {
        if (!isAllAttendancesMatchNickname(nickname, attendances)) {
            throw new IllegalArgumentException(nickname + "의 출석만 이용하여 생성가능합니다.");
        }
    }

    private boolean isAllAttendancesMatchNickname(String nickname, Attendance... attendances) {
        return Arrays.stream(attendances)
                .allMatch(attendance -> attendance.isEqualNickname(nickname));
    }

    public AttendanceCount getAttendanceCount(AttendanceDate endAttendanceDate) {
        return AttendanceCount.create(nickname, attendances, endAttendanceDate);
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
