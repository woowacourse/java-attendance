package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("출석 시간 테스트")
class ClassTimeTest {

    @Test
    @DisplayName("월요일 등교 시작 시간으로부터 차이를 반환한다")
    void shouldReturnDifferenceFromMondayStartTime() {
        // given
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 13, 0);

        // when
        int result = ClassTime.calculateAttendanceDifference(attendanceDateTime);

        // then
        assertThat(result)
                .isEqualTo(0);
    }
}
