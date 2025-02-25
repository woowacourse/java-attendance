package domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceTimeTest {

    @DisplayName("LocalTime 기반 AttendanceTime 객체 생성 테스트")
    @ParameterizedTest
    @MethodSource("provideCampusRunningTime")
    void generateAttendanceTimeTest(LocalTime openTime) {
        assertDoesNotThrow(() -> new AttendanceTime(openTime));
    }

    private static Stream<Arguments> provideCampusRunningTime() {
        return Stream.of(
                Arguments.arguments(LocalTime.of(8, 0)),
                Arguments.arguments(LocalTime.of(23, 0))
        );
    }

    @DisplayName("캠퍼스 운영 시간이 아닌 경우 예외 발생 테스트")
    @ParameterizedTest
    @MethodSource("provideCampusNotRunningTime")
    void campusNotRunningTest(LocalTime closeTime) {
        assertThatThrownBy(() -> new AttendanceTime(closeTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideCampusNotRunningTime() {
        return Stream.of(
                Arguments.arguments(LocalTime.of(7, 59)),
                Arguments.arguments(LocalTime.of(23, 1))
        );
    }
}
