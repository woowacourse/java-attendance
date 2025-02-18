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
        LocalDateTime now = LocalDateTime.now();
        Attendance beforeAttendance = new Attendance(crew, now);
        Attendance afterAttendance = new Attendance(crew, now);
        Attendances attendances = new Attendances(Set.of(beforeAttendance));

        Assertions.assertThatThrownBy(() -> attendances.add(afterAttendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("크루는 같은 날에 또 출석할 수 없습니다.");
    }
}
