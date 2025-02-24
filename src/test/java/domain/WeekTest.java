package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class WeekTest {

    @ParameterizedTest
    @MethodSource("methodSources")
    void 올바른_요일_반환(AttendanceDateTime attendanceDateTime, Week expectedDay) {
        // given
        Week day = Week.findByAttendanceTime(attendanceDateTime);

        // when
        // then
        Assertions.assertThat(day).isEqualTo(expectedDay);
    }

    private static Stream<Arguments> methodSources() {
        return Stream.of(
                Arguments.arguments(AttendanceDateTime.of(LocalDateTime.of(2024, 12, 16, 11, 11)), Week.MONDAY),
                Arguments.arguments(AttendanceDateTime.of(LocalDateTime.of(2024, 12, 17, 11, 11)), Week.TUESDAY)
        );
    }

    @ParameterizedTest
    @MethodSource("methodSources2")
    void 주말이_들어오면_예외_처리(AttendanceDateTime attendanceDateTime) {
        // given
        // when
        // then
        assertThatThrownBy(() -> Week.findByAttendanceTime(attendanceDateTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> methodSources2() {
        return Stream.of(
                Arguments.arguments(AttendanceDateTime.of(LocalDateTime.of(2024, 12, 14, 11, 11))),
                Arguments.arguments(AttendanceDateTime.of(LocalDateTime.of(2024, 12, 15, 11, 11)))
        );
    }
}
