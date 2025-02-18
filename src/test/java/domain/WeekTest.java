package domain;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class WeekTest {

    @ParameterizedTest
    @MethodSource("methodSources")
    void 올바른_요일_반환(LocalDateTime localDateTime, Week expectedDay) {
        // given
        Week day = Week.findByAttendanceTime(localDateTime);

        // when
        // then
        Assertions.assertThat(day).isEqualTo(expectedDay);
    }

    private static Stream<Arguments> methodSources() {
        return Stream.of(
                Arguments.arguments(LocalDateTime.of(2024, 12, 16, 11, 11), Week.MONDAY),
                Arguments.arguments(LocalDateTime.of(2024, 12, 17, 11, 11), Week.TUESDAY)
        );
    }

}
