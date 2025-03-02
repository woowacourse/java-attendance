package util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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

}
