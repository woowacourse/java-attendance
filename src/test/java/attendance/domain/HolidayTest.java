package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class HolidayTest {

    @ParameterizedTest
    @CsvSource({
            "2024-12-07",
            "2024-12-08",
            "2024-12-25",
    })
    @DisplayName("등교일이 아닌 경우 예외가 발생한다")
    void 등교일이_아닌_경우_예외가_발생한다(LocalDate date) {
        // given
        Holiday holiday = new Holiday();
        holiday.addHoliday(LocalDate.of(2024, 12, 25));
        String errorMessage = String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.",
                date.getMonthValue(),
                date.getDayOfMonth(),
                date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
        );

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> holiday.validateHoliday(date))
                .withMessage(errorMessage);
    }

    @ParameterizedTest
    @CsvSource({
            "2024-12-05, false",
            "2024-12-06, false",
            "2024-12-07, true",
            "2024-12-25, true",
    })
    void 휴일을_검사한다(LocalDate date, boolean excepted) {
        // given
        Holiday holiday = new Holiday();
        holiday.addHoliday(LocalDate.of(2024, 12, 25));

        // when & then
        assertThat(holiday.isHoliday(date))
                .isEqualTo(excepted);
    }

    @Test
    @DisplayName("동일한 휴일을 등록할 경우 예외가 발생한다")
    void 동일한_휴일을_등록할_경우_예외가_발생한다() {
        // given
        Holiday holiday = new Holiday();
        LocalDate date = LocalDate.of(2024, 12, 25);
        LocalDate date1 = LocalDate.of(2024, 12, 25);

        holiday.addHoliday(date1);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> holiday.addHoliday(date))
                .withMessage("[ERROR] 이미 추가된 휴일입니다.");
    }
}
