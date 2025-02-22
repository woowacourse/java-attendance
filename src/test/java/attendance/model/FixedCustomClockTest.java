package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FixedCustomClockTest {

    @Test
    void 시계가_설정한_날짜로_설정된다() {
        //given
        CustomClock clock = new FixedCustomClock(
                LocalDate.of(2024, 12, 16),
                Set.of(LocalDate.of(2024, 12, 25))
        );

        //when
        LocalDate now = clock.nowDate();

        //then
        assertThat(now).isEqualTo(LocalDate.of(2024, 12, 16));
    }

    @Test
    void 설정한_공휴일에_해당하는_날짜는_휴일로_판단한다() {
        //given
        CustomClock clock = new FixedCustomClock(
                LocalDate.of(2024, 12, 16),
                Set.of(LocalDate.of(2024, 12, 25))
        );

        //when
        boolean holiday = clock.isHoliday(LocalDate.of(2024, 12, 25));

        //then
        assertThat(holiday).isTrue();
    }

    @Test
    void 설정한_공휴일에_해당하지_않는_날짜는_휴일로_판단한다() {
        //given
        CustomClock clock = new FixedCustomClock(
                LocalDate.of(2024, 12, 16),
                Set.of(LocalDate.of(2024, 12, 25))
        );

        //when
        boolean holiday = clock.isHoliday(LocalDate.of(2024, 12, 1));

        //then
        assertThat(holiday).isFalse();
    }


}