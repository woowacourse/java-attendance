package domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceTimeTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("캠퍼스 운영 시간이 아니므로 예외가 발생한다.")
    void test1(final DayOfWeek dayOfWeek, final LocalTime localTime) {
        //should
        assertThatIllegalArgumentException().isThrownBy(() -> AttendanceTime.of(dayOfWeek, localTime));

    }

    private static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(DayOfWeek.MONDAY, LocalTime.of(7, 59)),
                Arguments.of(DayOfWeek.MONDAY, LocalTime.of(23, 1)),
                Arguments.of(DayOfWeek.TUESDAY, LocalTime.of(7, 59)),
                Arguments.of(DayOfWeek.TUESDAY, LocalTime.of(23, 1)),
                Arguments.of(DayOfWeek.WEDNESDAY, LocalTime.of(7, 59)),
                Arguments.of(DayOfWeek.WEDNESDAY, LocalTime.of(23, 1)),
                Arguments.of(DayOfWeek.THURSDAY, LocalTime.of(7, 59)),
                Arguments.of(DayOfWeek.THURSDAY, LocalTime.of(23, 1)),
                Arguments.of(DayOfWeek.FRIDAY, LocalTime.of(7, 59)),
                Arguments.of(DayOfWeek.FRIDAY, LocalTime.of(23, 1)),
                Arguments.of(DayOfWeek.SATURDAY, LocalTime.of(7, 59)),
                Arguments.of(DayOfWeek.SATURDAY, LocalTime.of(23, 1)),
                Arguments.of(DayOfWeek.SUNDAY, LocalTime.of(7, 59)),
                Arguments.of(DayOfWeek.SUNDAY, LocalTime.of(23, 1))
        );
    }

}
