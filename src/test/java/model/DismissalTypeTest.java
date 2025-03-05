package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DismissalTypeTest {

    @DisplayName("출석 상태별 횟수를 통해 제적 위험자인지 확인한다")
    @ParameterizedTest
    @MethodSource("resultArguments")
    void fromTest(final Map<AttendanceType, Integer> result, final DismissalType dismissalType) {
        assertThat(DismissalType.from(result)).isEqualTo(dismissalType);
    }

    private static Stream<Arguments> resultArguments() {
        return Stream.of(
                Arguments.arguments(Map.of(
                        AttendanceType.ABSENT, 3,
                        AttendanceType.LATE, 4,
                        AttendanceType.NONE, 5
                ), DismissalType.COUNSEL),
                Arguments.arguments(Map.of(
                        AttendanceType.ABSENT, 2,
                        AttendanceType.LATE, 5,
                        AttendanceType.NONE, 3
                ), DismissalType.COUNSEL),
                Arguments.arguments(Map.of(
                        AttendanceType.ABSENT, 1,
                        AttendanceType.LATE, 6,
                        AttendanceType.NONE, 9
                ), DismissalType.COUNSEL),
                Arguments.arguments(Map.of(
                        AttendanceType.ABSENT, 2,
                        AttendanceType.LATE, 3,
                        AttendanceType.NONE, 100
                ), DismissalType.COUNSEL),
                Arguments.arguments(Map.of(
                        AttendanceType.ABSENT, 0,
                        AttendanceType.LATE, 6,
                        AttendanceType.NONE, 0
                ), DismissalType.WARNING),
                Arguments.arguments(Map.of(
                        AttendanceType.ABSENT, 5,
                        AttendanceType.LATE, 3,
                        AttendanceType.NONE, 0
                ), DismissalType.DISMISSAL),
                Arguments.arguments(Map.of(
                        AttendanceType.ABSENT, 6,
                        AttendanceType.LATE, 0,
                        AttendanceType.NONE, 0
                ), DismissalType.DISMISSAL),
                Arguments.arguments(Map.of(
                        AttendanceType.ABSENT, 0,
                        AttendanceType.LATE, 18,
                        AttendanceType.NONE, 0
                ), DismissalType.DISMISSAL)
        );
    }
}
