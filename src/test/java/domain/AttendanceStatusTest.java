package domain;

import domain.dateTime.AttendanceDateTime;
import domain.dateTime.AttendanceTimePolicy;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceStatusTest {

    @DisplayName("출석시간에 맞는 상태를 반환한다.")
    @ParameterizedTest
    @MethodSource("methodSources")
    void validAttendanceStatus(final AttendanceDateTime attendanceDateTime, final AttendanceStatus expectedStatus) {
        // given
        // when
        final AttendanceTimePolicy attendanceTimePolicy = AttendanceTimePolicy.findByAttendanceDateTime(
                attendanceDateTime);
        final AttendanceStatus attendanceStatus = AttendanceStatus.findByAttendanceDateTime(attendanceDateTime,
                attendanceTimePolicy);

        // then
        Assertions.assertThat(attendanceStatus).isEqualTo(expectedStatus);
    }

    private static Stream<Arguments> methodSources() {
        return Stream.of(
                Arguments.arguments(AttendanceDateTime.from("2024-12-11 10:00"), AttendanceStatus.PRESENT),
                Arguments.arguments(AttendanceDateTime.from("2024-12-13 10:06"), AttendanceStatus.LATE),
                Arguments.arguments(AttendanceDateTime.from("2024-12-16 13:00"), AttendanceStatus.PRESENT),
                Arguments.arguments(AttendanceDateTime.from("2024-12-17 11:00"), AttendanceStatus.ABSENT)
        );
    }
}
