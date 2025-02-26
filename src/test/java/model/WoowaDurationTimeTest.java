package model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.WoowaDurationTime;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WoowaDurationTimeTest {

    @Test
    @DisplayName("캠퍼스 운영시간이 아닌 경우")
    void 입력한_시간이_운영시간인지_확인한다_1() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 9);
        LocalTime time = LocalTime.of(7, 0);

        // when
        boolean isDurationTime = WoowaDurationTime.isDurationTime(date, time);

        // then
        assertThat(isDurationTime).isFalse();
    }

    @Test
    @DisplayName("캠퍼스 운영시간인 경우")
    void 입력한_시간이_운영시간인지_확인한다_2() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 8);
        LocalTime time = LocalTime.of(8, 0);

        // when
        boolean isDurationTime = WoowaDurationTime.isDurationTime(date, time);

        // then
        assertThat(isDurationTime).isTrue();
    }
}
