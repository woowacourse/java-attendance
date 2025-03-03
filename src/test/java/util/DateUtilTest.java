package util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DateUtilTest {
    @Test
    @DisplayName("날짜로 출석 가능한 날짜들을 반환한다 - 26")
    void should_return_attendAble_dates_by_date_26() {
        // given
        int day = 26;

        // when
        List<Integer> attendAbleDates = DateUtil.getAttendAbleDates(day);

        // then
        assertThat(attendAbleDates).containsExactly(2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 16, 17, 18, 19, 20, 23, 24, 26);
    }

    @Test
    @DisplayName("날짜로 출석 가능한 날짜들을 반환한다 - 11")
    void should_return_attendAble_dates_by_date_11() {
        // given
        int day = 11;

        // when
        List<Integer> attendAbleDates = DateUtil.getAttendAbleDates(day);

        // then
        assertThat(attendAbleDates).containsExactly(2, 3, 4, 5, 6, 9, 10, 11);
    }

    @ParameterizedTest
    @DisplayName("출석 가능한 날인지 확인한다")
    @CsvSource(value = {"1, false", "2, true", "7, false", "25, false", "31, true"})
    void should_return_true_when_attendAble_date(int date, boolean expected) {
        // when
        boolean result = DateUtil.isAttendAbleDate(date);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
