package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceDayOfWeekTest {

    @Nested
    class InvalidCases {

        @ParameterizedTest
        @ValueSource(ints = {7, 8})
        void 출석_요일은_주말일수없다(int day) {
            // when & then
            assertThatThrownBy(() -> AttendanceDayOfWeek.from(
                LocalDate.of(
                    2024,
                    12,
                    day
                )))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 요일은 주말일 수 없습니다.");
        }
    }
}
