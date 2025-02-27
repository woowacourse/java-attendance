package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
