package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ClassScheduleTest {
    @DisplayName("등교일을 입력 받아 교육 시작 시간을 반환한다.")
    @ParameterizedTest
    @MethodSource("getStartTimeOfTestArgs")
    void getStartTimeOfTest(DayOfWeek day, LocalTime expectedValue) {
        assertThat(ClassSchedule.getStartTimeOf(day)).isEqualTo(expectedValue);
    }

    @DisplayName("등교하지 않는 요일의 교육 시작 시간을 확인할 경우 예외가 발생한다.")
    @ParameterizedTest
    @EnumSource(value = DayOfWeek.class, names = {"SATURDAY", "SUNDAY"})
    void getStartTimeOfExceptionTest(DayOfWeek day) {
        assertThatThrownBy(() -> ClassSchedule.getStartTimeOf(day)).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("입력 받은 날짜의 휴일 여부를 반환한다.")
    @ParameterizedTest
    @MethodSource("isDayOffTestArgs")
    void isDayOffTest(LocalDate date, boolean expectedValue) {
        assertThat(ClassSchedule.isDayOff(date)).isEqualTo(expectedValue);
    }

    static Stream<Arguments> getStartTimeOfTestArgs() {
        return Stream.of(
                Arguments.of(DayOfWeek.MONDAY, LocalTime.of(13, 0)),
                Arguments.of(DayOfWeek.TUESDAY, LocalTime.of(10, 0)),
                Arguments.of(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0)),
                Arguments.of(DayOfWeek.THURSDAY, LocalTime.of(10, 0)),
                Arguments.of(DayOfWeek.FRIDAY, LocalTime.of(10, 0))
        );
    }

    static Stream<Arguments> isDayOffTestArgs() {
        return Stream.of(
                Arguments.of(LocalDate.of(2024, 12, 1), true),
                Arguments.of(LocalDate.of(2024, 12, 3), false),
                Arguments.of(LocalDate.of(2024, 12, 25), true)
        );
    }
}
