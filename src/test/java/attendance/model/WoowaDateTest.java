package attendance.model;

import static attendance.util.DateFormatUtil.NOT_ATTENDABLE_FORMATTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.TestUtil;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WoowaDateTest {

    @DisplayName("등교가 가능한 날짜라면 정상적으로 WoowaDate가 생성된다")
    @Test
    void test_WoowaDate() {
        // given
        LocalDate possibleDate = LocalDate.of(2024, 12, 2);

        // when
        WoowaDate woowaDate = new WoowaDate(possibleDate, TestUtil.getClock());

        // then
        assertThat(woowaDate).isNotNull();
    }

    @DisplayName("주말 날짜로 생성시 예외가 발생한다")
    @Test
    void test_WoowaDate_error() {
        // given
        LocalDate impossibleDate = LocalDate.of(2024, 12, 1);

        // when & then
        assertThatThrownBy(() -> new WoowaDate(impossibleDate, TestUtil.getClock()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(impossibleDate.format(NOT_ATTENDABLE_FORMATTER));
    }

    @DisplayName("공휴일 날짜로 생성시 예외가 발생한다")
    @Test
    void test_holidayDate_error() {
        // given
        LocalDate impossibleDate = LocalDate.of(2024, 12, 25);

        // when & then
        assertThatThrownBy(() -> new WoowaDate(impossibleDate, TestUtil.getClock()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(impossibleDate.format(NOT_ATTENDABLE_FORMATTER));
    }

}