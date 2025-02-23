package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.ErrorCode;

public class CalendarTest {
    @Test
    @DisplayName("근무날짜가_아닌_경우_예외를_출력한다")
    void 근무날짜가_아닌_경우_예외를_출력한다() {
        AssertionsForClassTypes.assertThatThrownBy(
                        () -> Calendar.validateIsWorkingDay(7))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.SATURDAY_NOT_WORKING_DAY_FORMAT.format(7));

        AssertionsForClassTypes.assertThatThrownBy(
                        () -> Calendar.validateIsWorkingDay(1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.SUNDAY_NOT_WORKING_DAY_FORMAT.format(1));

        AssertionsForClassTypes.assertThatThrownBy(
                        () -> Calendar.validateIsWorkingDay(25))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.HOLIDAY_NOT_WORKING_DAY_FORMAT.format(25));
    }

    @Test
    @DisplayName("날짜를_넣으면_근무날짜인지_판별한다")
    void 날짜를_넣으면_근무날짜인지_판별한다() {
        assertThat(Calendar.checkIsWorkingDay(2)).isEqualTo(true);
        assertThat(Calendar.checkIsWorkingDay(1)).isEqualTo(false); // 일요일
    }

    @Test
    @DisplayName("날짜를_넣으면_월요일인지_판별한다")
    void 날짜를_넣으면_월요일인지_판별한다() {
        assertThat(Calendar.isMonday(2)).isEqualTo(true);
        assertThat(Calendar.isMonday(3)).isEqualTo(false);
    }

    @Test
    @DisplayName("근무_날짜_수를_반환한다")
    void 근무_날짜_수를_반환한다() {
        assertThat(Calendar.countWorkingDay()).isEqualTo(21);
    }
}