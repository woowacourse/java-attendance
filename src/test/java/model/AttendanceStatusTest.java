package model;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceStatusTest {

    @ParameterizedTest
    @DisplayName("AttedanceDateTime으로 출석 상태 검증을 잘 하는 지 성공 테스트")
    @MethodSource("findByAttendanceDateTimeSuccessSources")
    void findByAttendanceDateTImeSuccess(final AttendanceDateTime attendanceDateTime, final AttendanceStatus status) {
        // given
        // when
        final AttendanceStatus expected = AttendanceStatus.findByAttendanceDateTime(attendanceDateTime);
        // then
        Assertions.assertThat(status).isEqualTo(expected);
    }

    private static Stream<Arguments> findByAttendanceDateTimeSuccessSources() {
        return Stream.of(
                Arguments.arguments(AttendanceDateTime.of("2025-2-26 8:00"), AttendanceStatus.ATTENDANCE),
                Arguments.arguments(AttendanceDateTime.of("2025-2-26 10:05"), AttendanceStatus.ATTENDANCE),
                Arguments.arguments(AttendanceDateTime.of("2025-2-27 10:06"), AttendanceStatus.TARDINESS),
                Arguments.arguments(AttendanceDateTime.of("2025-2-27 10:30"), AttendanceStatus.TARDINESS),
                Arguments.arguments(AttendanceDateTime.of("2025-2-28 10:31"), AttendanceStatus.ABSENCE),
                Arguments.arguments(AttendanceDateTime.of("2025-2-24 13:00"), AttendanceStatus.ATTENDANCE)
        );
    }
}
