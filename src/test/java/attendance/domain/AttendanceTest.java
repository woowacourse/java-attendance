package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    @Nested
    class InvalidCases {

        @Test
        void 출석은_출석날짜_또는_출석시간을_가지않는다면_기록하지않는다() {
            // when & then
            assertThatThrownBy(() -> new AttendanceDateTime(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석은 출석 날짜와 출석 시간을 가지고 있어야 합니다.");
        }
    }
}
