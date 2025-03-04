package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceStatusTest {

    @ParameterizedTest
    @MethodSource("methodSources")
    void 출석_상태_체크(LocalTime localTime, AttendanceStatus expectedStatus) {
        // given
        Week day = Week.MONDAY;

        // when
        final AttendanceStatus attendanceStatus = AttendanceStatus.findByAttendanceTime(day, localTime);
        // then
        assertThat(attendanceStatus).isEqualTo(expectedStatus);
    }

    private static Stream<Arguments> methodSources() {
        return Stream.of(
                Arguments.arguments(LocalTime.of(12, 5), AttendanceStatus.ATTENDANCE),
                Arguments.arguments(LocalTime.of(13, 30), AttendanceStatus.TARDINESS),
                Arguments.arguments(LocalTime.of(13, 31), AttendanceStatus.ABSENCE)
        );
    }
}
