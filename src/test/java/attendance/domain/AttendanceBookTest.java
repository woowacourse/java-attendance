package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {

    @Nested
    class InvalidCases {

        @Test
        void 출석부는_크루_또는_출석기록을_가지지않는다면_기록하지않는다() {
            // when & then
            assertThatThrownBy(() -> new AttendanceBook(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석부는 크루와 출석 기록을 가지고 있어야 합니다.");
        }
    }
}
