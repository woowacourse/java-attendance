package global.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidatorTest {
    @Test
    void 등교일이_아닌_경우_예외가_발생한다() {
        LocalDate targetDate = LocalDate.of(2024, 12, 25);

        assertThatThrownBy(() -> Validator.validateIsNotWorkingDay(targetDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 등교일이라면_예외가_발생하지_않는다() {
        LocalDate targetDate = LocalDate.of(2024, 12, 4);

        assertThatNoException().isThrownBy(() -> Validator.validateIsNotWorkingDay(targetDate));
    }

    @Test
    void 캠퍼스_운영시간이_아니라면_예외가_발생한다() {
        LocalTime targetTime = LocalTime.of(6, 30);

        assertThatThrownBy(() -> Validator.validateIsInOperationTime(targetTime))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void 캠퍼스_운영시간_중이라면_예외가_발생하지_않는다() {
        LocalTime targetTime = LocalTime.of(10, 30);

        assertThatNoException().isThrownBy(() -> Validator.validateIsInOperationTime(targetTime));
    }

    @Test
    void 미래_날짜라면_예외가_발생한다() {
        LocalDate future = Date.TODAY.plusDays(3).toLocalDate();

        assertThatThrownBy(() -> Validator.validateIsFutureDate(future))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
