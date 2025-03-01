package util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    @Test
    @DisplayName("LocalDate를 M월 d월 E요일 형식으로 파싱한다.")
    void test2() {
        //given
        final LocalDate localDate = LocalDate.of(2024, 12, 13);

        //when
        final String koreanFormat = DateTimeParser.parseToLocalDateKoreanFormat(localDate);

        //then
        assertThat(koreanFormat).isEqualTo("12월 13일 금요일");

    }

}
