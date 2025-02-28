package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class ExpulsionTypeTest {

    @ParameterizedTest
    @DisplayName("출석 상태 갯수에 따른 제적 위험자 판별이 잘 되는 지")
    @MethodSource("findSuccessSources")
    void findSuccess(final AttendanceCountDto dto, final ExpulsionType expected) {

        // given
        // when
        final ExpulsionType expulsionType = ExpulsionType.find(dto);
        // then
        Assertions.assertThat(expulsionType).isEqualTo(expected);

    }

    private static Stream<Arguments> findSuccessSources() {
        return Stream.of(
                Arguments.arguments(new AttendanceCountDto(6, 0), ExpulsionType.EXPULSION),
                Arguments.arguments(new AttendanceCountDto(5, 3), ExpulsionType.EXPULSION),
                Arguments.arguments(new AttendanceCountDto(4, 7), ExpulsionType.EXPULSION),
                Arguments.arguments(new AttendanceCountDto(5, 1), ExpulsionType.INTERVIEW),
                Arguments.arguments(new AttendanceCountDto(4, 2), ExpulsionType.WARNING),
                Arguments.arguments(new AttendanceCountDto(0, 2), ExpulsionType.NONE)
        );
    }
}
