package model;

import java.time.DayOfWeek;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BusinessHoursTest {

    @ParameterizedTest
    @DisplayName("dateTime 으로 요소를 찾아낼 수 있는 지 성공 테스트")
    @MethodSource("findSuccessSourcesByAttendanceDateTime")
    void findSuccess(final AttendanceDateTime dateTime, final BusinessHours expected) {

        // given
        // when
        final BusinessHours businessHours = BusinessHours.find(dateTime);
        // then
        Assertions.assertThat(businessHours).isEqualTo(expected);
    }

    private static Stream<Arguments> findSuccessSourcesByAttendanceDateTime() {
        return Stream.of(
                Arguments.arguments(AttendanceDateTime.of("2025-2-27 11:36"), BusinessHours.THURSDAY),
                Arguments.arguments(AttendanceDateTime.of("2025-2-28 12:3"), BusinessHours.FRIDAY)
        );
    }

    @ParameterizedTest
    @DisplayName("영업시간 외의 dateTime일 때 예외 처리하는 테스트")
    @MethodSource("findFailureSourcesByNotWithOperatingTime")
    void findFailureByNotWithOperatingTime(final AttendanceDateTime dateTime) {

        // given
        // when
        // then
        Assertions.assertThatThrownBy(
                        () -> BusinessHours.find(dateTime)
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("영업시간이 아닌 시간에 출석을 할 수 없습니다.");
    }

    private static Stream<Arguments> findFailureSourcesByNotWithOperatingTime() {
        return Stream.of(
                Arguments.arguments(AttendanceDateTime.of("2025-2-27 7:59")),
                Arguments.arguments(AttendanceDateTime.of("2025-2-28 23:1"))
        );
    }

    @ParameterizedTest
    @DisplayName("주말일 때 예외 처리하는 테스트")
    @MethodSource("findFailureSourcesByWeekend")
    void findFailureByWeekend(final AttendanceDateTime dateTime) {

        // given
        // when
        // then
        Assertions.assertThatThrownBy(
                        () -> BusinessHours.find(dateTime)
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("주말에는 영업하지 않습니다");
    }

    private static Stream<Arguments> findFailureSourcesByWeekend() {
        return Stream.of(
                Arguments.arguments(AttendanceDateTime.of("2025-2-22 8:00")),
                Arguments.arguments(AttendanceDateTime.of("2025-2-23 23:00"))
        );
    }

    @ParameterizedTest
    @DisplayName("String 이름을 입력받았을 때 동일 유무를 파악할 수 있는 지 성공 테스트")
    @MethodSource("equalsNameSuccessSources")
    void equalsNameSuccess(final String dayOfWeekName, final BusinessHours businessHours) {

        // given
        // when
        // then
        Assertions.assertThat(dayOfWeekName).isEqualTo(businessHours.name());
    }

    private static Stream<Arguments> equalsNameSuccessSources() {
        return Stream.of(
                Arguments.arguments(DayOfWeek.MONDAY.name(), BusinessHours.MONDAY.name()),
                Arguments.arguments(DayOfWeek.TUESDAY.name(), BusinessHours.TUESDAY.name())
        );
    }
}
