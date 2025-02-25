package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceStateTest {

    @DisplayName("출석 시간과 요일로 출석 상태를 계산하여 반환한다.")
    @ParameterizedTest
    @MethodSource("provideLocalDateTime")
    void statusReturn(LocalDateTime localDateTime, String expected) {
        // given
        AttendanceState actual = AttendanceState.findStateBy(localDateTime);

        // when & then
        assertThat(actual.getDescription()).isEqualTo(expected);
    }

    static Stream<Arguments> provideLocalDateTime() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 12, 2, 13, 5), "출석"),
                Arguments.of(LocalDateTime.of(2024, 12, 2, 13, 6), "지각"),
                Arguments.of(LocalDateTime.of(2024, 12, 2, 13, 31), "결석"),
                Arguments.of(LocalDateTime.of(2024, 12, 4, 10, 5), "출석"),
                Arguments.of(LocalDateTime.of(2024, 12, 4, 10, 6), "지각"),
                Arguments.of(LocalDateTime.of(2024, 12, 4, 10, 31), "결석"));
    }

}
