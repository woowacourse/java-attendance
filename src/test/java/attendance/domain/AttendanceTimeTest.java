package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceTimeTest {

    @Nested
    class InvalidCases {

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
