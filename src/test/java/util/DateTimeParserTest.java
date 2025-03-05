package util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import common.SystemDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class DateTimeParserTest {


    @Test
    @DisplayName("yyyy-MM-dd HH:mm 형태의 문자열을 LocalDateTime으로 파싱한다")
    void test1() {
        //given
        final String dateTime = "2024-12-13 10:08";

        //when
        final LocalDateTime localDateTime = DateTimeParser.parseToLocalDateTime(dateTime);

        //then
        assertThat(localDateTime.getYear()).isEqualTo(2024);
        assertThat(localDateTime.getMonth().getValue()).isEqualTo(12);
        assertThat(localDateTime.getDayOfMonth()).isEqualTo(13);

    }


    @ParameterizedTest
    @ValueSource(strings = {"2024/12/13 10:08", "2024-12-1310:08"})
    @DisplayName("yyyy-MM-dd HH:mm 형식이 아니므로 예외가 발생한다.")
    void test6(final String dateTime) {
        //should
        assertThatIllegalArgumentException().isThrownBy(() -> DateTimeParser.parseToLocalDateTime(dateTime));
    }

    @ParameterizedTest
    @ValueSource(strings = {"01/11", "9:59"})
    @DisplayName("HH:mm 형식이 아니므로 예외가 발생한다.")
    void test7(final String dateTime) {
        //should
        assertThatIllegalArgumentException().isThrownBy(() -> DateTimeParser.parseToLocalTime(dateTime));
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("일을 LocalDate로 변환한다.")
    void test8(final String dayOfMonth, final LocalDate expected) {
        //should
        assertThat(DateTimeParser.parseToLocalDate(dayOfMonth)).isEqualTo(expected);
    }

    private static Stream<Arguments> test8() {
        return Stream.of(
                Arguments.of("1", SystemDate.NOW.getDate().withDayOfMonth(1)),
                Arguments.of("2", SystemDate.NOW.getDate().withDayOfMonth(2)),
                Arguments.of("22", SystemDate.NOW.getDate().withDayOfMonth(22))
        );
    }

}
