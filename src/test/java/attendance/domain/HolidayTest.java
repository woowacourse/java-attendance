package attendance.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class HolidayTest {

    @Test
    void 출석날짜가_주말일_경우_예외를_반환한다() {
        // given
        LocalDate sunday = LocalDate.of(2024,12, 1);
        LocalDate saturday = LocalDate.of(2024,12, 7);
        LocalTime presenceTime = LocalTime.of(8, 10);

        // when & then
        assertThatThrownBy(() -> Holiday.check(sunday))
            .hasMessage("[ERROR] 12월 1일은 등교일이 아닙니다.");
        assertThatThrownBy(() -> Holiday.check(saturday))
            .hasMessage("[ERROR] 12월 7일은 등교일이 아닙니다.");
    }

    @Test
    void 출석날짜가_크리스마스일_경우_예외를_반환한다() {
        // given
        LocalDate christmas = LocalDate.of(2024,12, 25);
        LocalTime presenceTime = LocalTime.of(8, 10);

        // when & then
        assertThatThrownBy(() -> Holiday.check(christmas))
            .hasMessage("[ERROR] 12월 25일은 등교일이 아닙니다.");
    }
}
