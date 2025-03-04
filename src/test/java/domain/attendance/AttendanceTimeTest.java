package domain.attendance;

import java.time.LocalTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceTimeTest {
    @DisplayName("오전 8시-오후 11시까지 캠퍼스 운영시간이다")
    @ParameterizedTest
    @MethodSource("operateTimes")
    void test(LocalTime time) {
        boolean operatingTime = AttendanceTime.isOperatingTime(time);
        Assertions.assertThat(operatingTime).isTrue();
    }

    private static Stream<Arguments> operateTimes() {
        return Stream.of(
                Arguments.arguments(LocalTime.of(8, 0)),
                Arguments.arguments(LocalTime.of(23, 0))
        );
    }
}