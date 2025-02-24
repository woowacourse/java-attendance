package util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DateTimeParserTest {

    @Test
    @DisplayName("문자열을 날짜 객체로 파싱한다.")
    void paseToLocalDateTest() {
        //given
        final String time = "2024-12-13 10:30";

        //when
        final LocalDateTime localDateTime = DateTimeParser.parseToLocalDateTime(time);

        //then
        assertThat(localDateTime).isEqualTo(LocalDateTime.of(2024, 12, 13, 10, 30));

    }

}
