package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.HolidayManager;

import java.util.stream.Stream;

class HolidayManagerTest {

    @ParameterizedTest
    @DisplayName("공휴일을 잘 찾아내는 지")
    @MethodSource("isHolidaySources")
    void isHoliday(final AttendanceDateTime attendanceDateTime, final Boolean expected) {

        // given
        // when
        final boolean result = HolidayManager.isHoliday(attendanceDateTime);
        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    // isHolidaySources
    private static Stream<Arguments> isHolidaySources() {
        return Stream.of(
                Arguments.arguments(AttendanceDateTime.of("2024-12-25 10:00"), Boolean.TRUE),
                Arguments.arguments(AttendanceDateTime.of("2024-12-24 10:00"), Boolean.FALSE)
        );
    }
}