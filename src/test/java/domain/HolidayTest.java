package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HolidayTest {

    @Nested
    @DisplayName("성공 테스트")
    class Success {
        @ParameterizedTest
        @MethodSource
        @DisplayName("주말 또는 공휴일을 확인한다.")
        void isHolidayTest(final LocalDate date, final boolean isHoliday) {
            //should
            assertThat(Holiday.isHoliday(date)).isEqualTo(isHoliday);
        }

        private static Stream<Arguments> isHolidayTest() {
            return Stream.of(
                    Arguments.of(LocalDate.of(2024, 12, 13), false),
                    Arguments.of(LocalDate.of(2024, 12, 14), true),
                    Arguments.of(LocalDate.of(2024, 12, 15), true),
                    Arguments.of(LocalDate.of(2024, 12, 25), true)
            );
        }
    }

}
