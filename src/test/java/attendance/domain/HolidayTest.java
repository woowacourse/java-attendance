package attendance.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class HolidayTest {

    @Test
    void 날짜가_주말인지_판단한다() {
        // given
        LocalDate sunday = LocalDate.of(2024,12, 1);
        LocalDate saturday = LocalDate.of(2024,12, 7);

        // when & then
        assertThat(Holiday.check(sunday)).isTrue();
        assertThat(Holiday.check(saturday)).isTrue();
    }

    @Test
    void 출석날짜가_주말일_경우_예외를_반환한다() {
        // given
        LocalDate sunday = LocalDate.of(2024,12, 1);
        LocalDate saturday = LocalDate.of(2024,12, 7);

        // when & then
        assertThatThrownBy(() -> Holiday.validateWeekDay(sunday))
            .hasMessage("[ERROR] 12월 1일은 등교일이 아닙니다.");
        assertThatThrownBy(() -> Holiday.validateWeekDay(saturday))
            .hasMessage("[ERROR] 12월 7일은 등교일이 아닙니다.");
    }

    @Test
    void 출석날짜가_크리스마스일_경우_예외를_반환한다() {
        // given
        LocalDate christmas = LocalDate.of(2024,12, 25);

        // when & then
        assertThatThrownBy(() -> Holiday.validateWeekDay(christmas))
            .hasMessage("[ERROR] 12월 25일은 등교일이 아닙니다.");
    }
}
