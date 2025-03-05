package attendance.domain;

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
            "7, 59",
            "23, 1"
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
            "8, 0",
            "23, 0"
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

    @ParameterizedTest
    @CsvSource(value = {
            "27, true",
            "26, false"
    })
    void 입력_받은_날짜와_출석_기록_날짜가_같은지_비교한다(final int date, final boolean expectedResult) {

        // given
        AttendanceTime attendanceTime = new AttendanceTime(LocalDate.of(2025, 2, 27), 10, 10);

        // when
        final boolean result = attendanceTime.isSameDay(LocalDate.of(2025, 2, date));

        // then
        Assertions.assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void 입력_받은_이름과_날짜_일시로_출석_기록을_수정한다() {

        // given
        final AttendanceTime attendanceTime = new AttendanceTime(LocalDate.of(2025, 2, 27), 10, 10);
        final int targetHour = 10;
        final int targetMinute = 20;

        // when
        attendanceTime.modify(targetHour, targetMinute);

        // then
        Assertions.assertThat(attendanceTime.getHour()).isEqualTo(targetHour);
        Assertions.assertThat(attendanceTime.getMinute()).isEqualTo(targetMinute);
    }

    @ParameterizedTest
    @MethodSource("attendanceTimeAndResult")
    void 출석_시간이_기본_결석_날짜인지_확인한다(final AttendanceTime attendanceTime, final boolean expectedResult) {

        // given

        // when
        final boolean result = attendanceTime.isDefaultAbsent();

        // then
        Assertions.assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("localTimeAndResult")
    void 입력_받은_시간이_출석_시간보다_이전인지_판단한다(final LocalTime localTime, final boolean expectResult) {

        // given
        final AttendanceTime attendanceTime = new AttendanceTime(LocalDate.of(2025, 2, 27), 10, 10);

        // when
        final boolean result = attendanceTime.isBefore(localTime);

        // then
        Assertions.assertThat(result).isEqualTo(expectResult);
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

    public static Stream<Arguments> attendanceTimeAndResult() {

        return Stream.of(
                Arguments.of(new AttendanceTime(LocalDate.of(2025, 2, 27)), true),
                Arguments.of(new AttendanceTime(LocalDate.of(2025, 2, 27), 10, 10), false)
        );
    }

    public static Stream<Arguments> localTimeAndResult() {

        return Stream.of(
                Arguments.of(LocalTime.of(10, 9), false),
                Arguments.of(LocalTime.of(10, 11), true)
        );
    }
}
