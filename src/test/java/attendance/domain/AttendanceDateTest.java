package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceDateTest {

    @Nested
    class InvalidCases {

        @ParameterizedTest
        @ValueSource(ints = {0, 13})
        void 출석_날짜는_1월부터_12월까지만_지원한다(int month) {
            // when & then
            assertThatThrownBy(() -> new AttendanceDate(2024, month, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("월은 1 이상 12 이하여야 합니다.");
        }

        @ParameterizedTest
        @ValueSource(ints = {0, 32})
        void 출석_날짜는_1일부터_31일까지만_지원한다(int day) {
            // when & then
            assertThatThrownBy(() -> new AttendanceDate(2024, 12, day))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("일은 1 이상 31 이하여야 합니다.");
        }

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

        @ParameterizedTest
        @CsvSource(
            {
                "2024, 12, 25",  // 크리스마스 (공휴일)
                "2024, 12, 15",  // 일요일 (주말)
                "2024, 12, 21"   // 토요일 (주말)
            }
        )
        void 출석_날짜가_주말_또는_공휴일이라면_기록하지_않는다(
            int year,
            int month,
            int day
        ) {
            // when & then
            assertThatThrownBy(() -> new AttendanceDate(year, month, day))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 날짜는 주말 또는 공휴일은 지원하지 않습니다.");
        }
    }
}
