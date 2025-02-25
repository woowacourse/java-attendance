package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class EducationTimeTest {

    @ParameterizedTest
    @CsvSource({
            "2024-12-02T13:06:00, 6",
            "2024-12-02T12:59:00, 0",
            "2024-12-03T10:06:00, 6",
            "2024-12-03T09:59:00, 0",
    })
    @DisplayName("요일에 알맞은 지각 시간을 반환한다.")
    void 요일에_알맞은_지각_시간을_반환한다(LocalDateTime attendanceTime, int expected) {
        // when
        int result = EducationTime.calculateOverTime(attendanceTime);

        // then
        assertThat(result)
                .isEqualTo(expected);
    }
}
