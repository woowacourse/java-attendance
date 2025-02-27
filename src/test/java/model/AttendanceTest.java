package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class AttendanceTest {

    @ParameterizedTest
    @DisplayName("attendanceDateTime 자료형의 입력값으로 객체를 잘 생성하는 지 성공 테스트")
    @MethodSource("ofSuccessSources")
    void ofSuccess(final AttendanceDateTime attendanceDateTime, final AttendanceStatus expected) {

        // given
        // when
        final Attendance attendance = Attendance.of(attendanceDateTime);
        final AttendanceStatus status = attendance.getAttendanceStatus();

        // then
        Assertions.assertThat(status).isEqualTo(expected);
    }

    private static Stream<Arguments> ofSuccessSources() {
        return Stream.of(
                Arguments.arguments(AttendanceDateTime.of("2025-2-27 10:5"), AttendanceStatus.ATTENDANCE),
                Arguments.arguments(AttendanceDateTime.of("2025-2-28 10:31"), AttendanceStatus.ABSENCE)
        );
    }
}
