package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceTimeTest {

    @Nested
    class InvalidCases {

        @ParameterizedTest
        @ValueSource(ints = {-1, 24})
        void 출석시간은_0시부터_23시까지만_지원한다(int hour) {
            // when & then
            assertThatThrownBy(() -> new AttendanceTime(hour, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시간은 0 이상 23 이하여야 합니다.");
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, 60})
        void 출석시간은_0분부터_59분까지만_지원한다(int minute) {
            // when & then
            assertThatThrownBy(() -> new AttendanceTime(7, minute))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("분은 0 이상 59 이하여야 합니다.");
        }

        @ParameterizedTest
        @CsvSource(
            {
                "7, 59",
                "23, 01"
            }
        )
        void 출석시간이_캠퍼스_운영시간이_아니라면_기록하지않는다(
            Integer hour,
            Integer minute
        ) {
            // when & then
            assertThatThrownBy(() -> new AttendanceTime(hour, minute))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 시간은 캠퍼스 운영시간만 지원합니다.");
        }
    }
}
