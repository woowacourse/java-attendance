import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InputParserTest {
    @Test
    @DisplayName("시간 형태의 문자열을 입력하면 LocalDateTime 으로 반환한다")
    void test1() {
        // given
        String input = "13:45";

        // when & then
        assertThat(InputParser.parseTime(input))
                .isEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 45)));
    }

    @Test
    @DisplayName("LocalDateTime 을 12월 05일 목요일 09:59 와 같은 형태로 반환한다")
    void test2() {
        // given
        LocalDateTime time = LocalDateTime.of(2024, 12, 5, 9, 59);

        // when & then
        assertThat(InputParser.parseDateTimeToString(time)).isEqualTo("12월 05일 목요일 09:59");
    }
}
