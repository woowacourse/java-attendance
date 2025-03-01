package util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Test
    @DisplayName("DayOfWeek를 E요일 형식으로 파싱한다.")
    void test3() {
        //given
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;

        //when
        final String koreanFormat = DateTimeParser.parseToDayOfWeekKoreanFormat(dayOfWeek);

        //then
        assertThat(koreanFormat).isEqualTo("월요일");
    }

    @Test
    @DisplayName("LocalDateTime을 M월 d일 E요일 HH:mm 형식으로 파싱한다.")
    void test4() {
        //given
        final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 9, 8, 59);

        //when
        final String koreanFormat = DateTimeParser.parseToLocalDateTimeKoreanFormat(localDateTime);

        //then
        assertThat(koreanFormat).isEqualTo("12월 09일 월요일 08:59");

    }

    @Test
    @DisplayName("LocalTime을 HH:mm 형식으로 파싱한다.")
    void test5() {
        //given
        final LocalTime localTime = LocalTime.of(9, 1);

        //when
        final String koreanFormat = DateTimeParser.parseToLocalTimeKoreanFormat(localTime);

        //then
        assertThat(koreanFormat).isEqualTo("09:01");

    }

}
