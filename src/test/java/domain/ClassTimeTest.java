package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ClassTimeTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("요일에 맞는 강의 시작 시간을 찾는다.")
    void test1(final DayOfWeek dayOfWeek, final LocalTime expected) {
        //should
        assertThat(ClassTime.findByDayOfWeek(dayOfWeek)).isEqualTo(expected);

    }

    private static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(DayOfWeek.MONDAY, LocalTime.of(13, 0)),
                Arguments.of(DayOfWeek.TUESDAY, LocalTime.of(10, 0)),
                Arguments.of(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0)),
                Arguments.of(DayOfWeek.THURSDAY, LocalTime.of(10, 0)),
                Arguments.of(DayOfWeek.FRIDAY, LocalTime.of(10, 0))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("요일에 맞는 강의 시간을 찾지 못하여 예외가 발생한다.")
    void test2(final DayOfWeek dayOfWeek) {
        //should
        assertThatIllegalArgumentException().isThrownBy(() -> ClassTime.findByDayOfWeek(dayOfWeek));

    }

    private static Stream<DayOfWeek> test2() {
        return Stream.of(
                DayOfWeek.SATURDAY,
                DayOfWeek.SUNDAY
        );
    }

}
