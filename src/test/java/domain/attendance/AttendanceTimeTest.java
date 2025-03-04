package domain.attendance;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceTimeTest {
    @DisplayName("오전 8시-오후 11시까지 캠퍼스 운영시간이다")
    @ParameterizedTest
    @MethodSource("operateTimes")
    void test(LocalTime time, boolean isOperating) {
        boolean operatingTime = AttendanceTime.isOperatingTime(time);
        Assertions.assertThat(operatingTime).isEqualTo(isOperating);
    }

    private static Stream<Arguments> operateTimes() {
        return Stream.of(
                Arguments.arguments(LocalTime.of(8, 0), true),
                Arguments.arguments(LocalTime.of(23, 0), true),
                Arguments.arguments(LocalTime.of(7, 59), false),
                Arguments.arguments(LocalTime.of(23, 1), false)
        );
    }

    @DisplayName("요일 별 출석시간 5분 이내라면 출석이다")
    @ParameterizedTest
    @MethodSource("attendanceTime")
    void test2(LocalDateTime dateTime) {
        boolean isAttendance = AttendanceTime.isAttendance(dateTime);
        Assertions.assertThat(isAttendance).isTrue();
    }

    private static Stream<Arguments> attendanceTime() {
        return Stream.of(
                Arguments.arguments(LocalDateTime.of(2025, 3, 3, 13, 5)),
                Arguments.arguments(LocalDateTime.of(2025, 3, 4, 10, 5))
        );
    }
}