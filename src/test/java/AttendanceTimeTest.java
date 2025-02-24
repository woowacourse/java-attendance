import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.date.AttendanceTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class AttendanceTimeTest {

    private static Stream<Arguments> testCasesForTestIsLate() {
        return Stream.of(
                Arguments.of(1, 13, 0, false),
                Arguments.of(1, 13, 6, true),
                Arguments.of(2, 10, 0, false),
                Arguments.of(2, 10, 6, true)
        );
    }

    @ParameterizedTest
    @MethodSource("testCasesForTestIsLate")
    @DisplayName("지각 여부를 판단한다")
    void testIsLate(int dayOfWeek, int hour, int minute, boolean expected) {
        // given
        AttendanceTime attendanceTime = new AttendanceTime(hour, minute);

        // when
        boolean actual = attendanceTime.isLate(dayOfWeek);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testCasesForTestIsAbsence() {
        return Stream.of(
                Arguments.of(1, 13, 5, false),
                Arguments.of(1, 13, 31, true),
                Arguments.of(2, 10, 3, false),
                Arguments.of(2, 10, 31, true)
        );
    }

    @ParameterizedTest
    @MethodSource("testCasesForTestIsAbsence")
    @DisplayName("결석 여부를 판단한다")
    void testIsAbsence(int dayOfWeek, int hour, int minute, boolean expected) {
        // given
        AttendanceTime attendanceTime = new AttendanceTime(hour, minute);

        // when
        boolean actual = attendanceTime.isAbsence(dayOfWeek);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testCasesForTestIsOpenTime() {
        return Stream.of(
                Arguments.of(23, 5, false),
                Arguments.of(18, 0, true),
                Arguments.of(7, 59, false),
                Arguments.of(8, 0, true)
        );
    }

    @ParameterizedTest
    @MethodSource("testCasesForTestIsOpenTime")
    @DisplayName("운영 시간 여부를 판단한다")
    void testIsAbsence(int hour, int minute, boolean expected) {
        // given
        AttendanceTime attendanceTime = new AttendanceTime(hour, minute);

        // when
        boolean actual = attendanceTime.isOpenTime();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {30, 25, 38, -1})
    @DisplayName("시각 형식이 올바르지 않은 경우 예외를 던진다")
    void testValidateHourThrowsException(int hour) {
        // when & then
        assertThatThrownBy(() -> new AttendanceTime(hour, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시각 형식이 올바르지 않습니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 100, 80, -59})
    @DisplayName("분 형식이 올바르지 않은 경우 예외를 던진다")
    void testValidateMinuteThrowsException(int minute) {
        // when & then
        assertThatThrownBy(() -> new AttendanceTime(0, minute))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("분 형식이 올바르지 않습니다.");
    }

}
