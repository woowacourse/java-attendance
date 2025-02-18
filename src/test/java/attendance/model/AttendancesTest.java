package attendance.model;

import java.time.LocalDateTime;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("출석 내역 테스트")
class AttendancesTest {

    @DisplayName("크루는 같은 날에 또 출석할 경우 예외가 발생한다")
    @Test
    void shouldThrowException_WhenCrewAgainAttendanceInToday() {
        Crew crew = new Crew("포비");
        CrewGroup crewGroup = new CrewGroup(Set.of(crew));
        LocalDateTime now = LocalDateTime.now();
        Attendance beforeAttendance = new Attendance(crew, now);
        Attendance afterAttendance = new Attendance(crew, now);
        Attendances attendances = new Attendances(crewGroup, Set.of(beforeAttendance));

        Assertions.assertThatThrownBy(() -> attendances.add(afterAttendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("크루는 같은 날에 또 출석할 수 없습니다.");
    }

    @DisplayName("등록되지 않은 닉네임을 사용하려고 하는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenUseNotExistNickname() {
        Crew crew = new Crew("포비");
        CrewGroup crewGroup = new CrewGroup(Set.of(crew));
        LocalDateTime now = LocalDateTime.now();
        Attendance attendance = new Attendance(crew, now);
        Attendances attendances = new Attendances(crewGroup, Set.of(attendance));

        String notExistNickname = "네오";
        Assertions.assertThatThrownBy(() -> attendances.validateAttendance(notExistNickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등록되지 않은 닉네임입니다.");
    }
}
