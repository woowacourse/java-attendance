package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("출석 시간 테스트")
class ClassTimeTest {

    @ParameterizedTest
    @CsvSource({
            "2024-12-02T13:00, 0",
            "2024-12-03T10:00, 0",
            "2024-12-04T10:00, 0",
            "2024-12-05T10:00, 0",
            "2024-12-06T10:00, 0",
    })
    @DisplayName("등교 시작 시간으로부터 차이를 반환한다")
    void shouldReturnDifferenceFromMondayStartTime(LocalDateTime attendanceDateTime, int excepted) {
        // when
        int result = ClassTime.calculateAttendanceDifference(attendanceDateTime);

        // then
        assertThat(result).isEqualTo(excepted);
    }
}
