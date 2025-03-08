package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.dateTime.AttendanceDateTime;
import domain.dateTime.AttendanceTimePolicy;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceTimePolicyTest {

    @DisplayName("날짜에 맞는 올바른 요일을 찾아서 반환한다.")
    @ParameterizedTest
    @MethodSource("methodSources")
    void validDayOfWeek(final AttendanceDateTime attendanceDateTime, final AttendanceTimePolicy attendanceTimePolicy) {
        // given
        // when
        final AttendanceTimePolicy policy = AttendanceTimePolicy.findByAttendanceDateTime(attendanceDateTime);

        // then
        assertThat(policy).isEqualTo(attendanceTimePolicy);
    }

    private static Stream<Arguments> methodSources() {
        return Stream.of(
                Arguments.arguments(AttendanceDateTime.from("2024-12-11 10:00"), AttendanceTimePolicy.WEDNESDAY),
                Arguments.arguments(AttendanceDateTime.from("2024-12-12 10:00"), AttendanceTimePolicy.THURSDAY),
                Arguments.arguments(AttendanceDateTime.from("2024-12-13 10:00"), AttendanceTimePolicy.FRIDAY)
        );
    }


    @ParameterizedTest
    @DisplayName("날짜에 맞는 올바른 요일을 찾아서 반환한다.")
    @MethodSource("methodSources2")
    void invalidDayOfWeek(final AttendanceDateTime attendanceDateTime) {
        // given
        // when
        // then
        assertThatThrownBy(() -> AttendanceTimePolicy.findByAttendanceDateTime(attendanceDateTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> methodSources2() {
        return Stream.of(
                Arguments.arguments(AttendanceDateTime.from("2024-12-14 10:00")),
                Arguments.arguments(AttendanceDateTime.from("2024-12-15 10:00"))
        );
    }
}
