package util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CurrentTest {
    @Test
    @DisplayName("2024-12-11을 오늘로 가정하여 오늘을 가져온다")
    void should_return_today_of_2024_12_11() {
        LocalDate today = Current.getToday();

        // then
        assertThat(today).isEqualTo(LocalDate.of(2024, 12, 11));
    }

    @Test
    @DisplayName("2024-12-11을 오늘로 가정하여 이번 달 문자열 값을 가져온다")
    void should_return_String_of_this_month_of_2024_12_11() {
        String thisMonth = Current.getStringOfThisMonth();

        // then
        assertThat(thisMonth).isEqualTo("2024-12");
    }
}
