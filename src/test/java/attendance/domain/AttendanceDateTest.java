package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AttendanceDateTest {

    @Nested
    class InvalidCases {

        @Test
        void 출석_날짜가_2024년_12월이_아니라면_기록하지않는다() {
            // given
            int year = 2025;
            int month = 2;
            int day = 30;

            // when & then
            assertThatThrownBy(() -> new AttendanceDate(year, month, day))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 날짜는 2024년 12월만 지원합니다.");
        }

        @Test
        void 출석_날짜가_2024년_12월이라도_일자가_31일을_넘어가면_기록하지않는다() {
            // given
            int year = 2024;
            int month = 12;
            int day = 32;

            // when & then
            assertThatThrownBy(() -> new AttendanceDate(year, month, day))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 날짜는 2024년 12월만 지원합니다.");
        }
    }
}
