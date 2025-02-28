package domain;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceTest {
    @DisplayName("주말 또는 공휴일에 출석하면 예외를 뱉는다")
    @ParameterizedTest
    @MethodSource("holidayOrWeekend")
    void test(int month, int day) {
        // given
        LocalDateTime holidayOrWeekend = LocalDateTime.of(2024, month, day, 10, 0);

        // when & then
        Assertions.assertThatThrownBy(() -> new Attendance(holidayOrWeekend))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말 또는 공휴일에는 출석할 수 없습니다");
    }

    private static Stream<Arguments> holidayOrWeekend() {
        return Stream.of(
                Arguments.arguments(12, 25),
                Arguments.arguments(12, 29)
        );
    }
}