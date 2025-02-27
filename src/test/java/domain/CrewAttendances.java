package domain;

import attendance.domain.Attendance;
import java.util.List;

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
}
