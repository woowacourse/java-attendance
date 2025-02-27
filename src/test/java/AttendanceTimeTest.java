import attendance.model.AttendanceTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceTimeTest {

    @Test
    void 입력_받은_날짜가_등교_날짜가_아닐_경우_예외를_발생한다() {

        // given
        LocalDate localdate = LocalDate.of(2025, 3, 1);
        int hour = 10, minute = 10;

        // when & then
        Assertions.assertThatThrownBy(() -> new AttendanceTime(localdate, hour, minute))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등교 날짜가 아닙니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {
            "7,59",
            "23,1"
    })
    void 입력_받은_시간이_캠퍼스_운영_시간이_아니면_예외를_발생한다(final int hour, final int minute) {

        // given
        final LocalDate localdate = LocalDate.of(2025, 2, 27);

        // when & then
        Assertions.assertThatThrownBy(() -> new AttendanceTime(localdate, hour, minute))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {
            "8,0",
            "23,0"
    })
    void 입력_받은_시간이_유효한_시간이면_출석_시간이_생성된다(final int hour, final int minute) {

        // given
        final LocalDate localdate = LocalDate.of(2025, 2, 27);

        // when & then
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> new AttendanceTime(localdate, hour, minute));
    }

    @ParameterizedTest
    @MethodSource("dateAndResult")
    void 출석_날짜가_월요일인지_판단한다(final int date, final boolean expectedResult) {

        // given
        final LocalDate localdate = LocalDate.of(2025, 2, date);

        // when
        final boolean result = new AttendanceTime(localdate, 10, 10).isMonday();

        // then
        Assertions.assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("hourAndMinuteAndResult")
    void 현재_출석_시간이_입력받은_시간보다_이전인지_판단한다(final LocalTime time, final boolean expectedResult) {

        // given
        final LocalDate localdate = LocalDate.of(2025, 2, 10);

        // when
        final boolean result = new AttendanceTime(localdate, 10, 5).isBefore(time);

        // then
        Assertions.assertThat(result).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> dateAndResult() {

        return Stream.of(
                Arguments.of(24, true),
                Arguments.of(27, false)
        );
    }

    public static Stream<Arguments> hourAndMinuteAndResult() {

        return Stream.of(
                Arguments.of(LocalTime.of(10, 6), true),
                Arguments.of(LocalTime.of(10, 4), false)
        );
    }
}
