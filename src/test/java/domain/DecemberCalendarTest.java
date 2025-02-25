package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DecemberCalendarTest {
    @Test
    @DisplayName("날짜를 입력받아 날짜에 해당하는 값을 반환한다.")
    void Judge_Working_Day() {
        assertThat(DecemberCalendar.judgeWorkingDay(LocalDate.of(2024, 12, 1)))
                .isEqualTo("일요일");
        assertThat(DecemberCalendar.judgeWorkingDay(LocalDate.of(2024, 12, 2)))
                .isEqualTo("월요일");
        assertThat(DecemberCalendar.judgeWorkingDay(LocalDate.of(2024, 12, 3)))
                .isEqualTo("근무일");
        assertThat(DecemberCalendar.judgeWorkingDay(LocalDate.of(2024, 12, 7)))
                .isEqualTo("토요일");
        assertThat(DecemberCalendar.judgeWorkingDay(LocalDate.of(2024, 12, 25)))
                .isEqualTo("공휴일");
    }
}