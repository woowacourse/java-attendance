package attendance.controller.validator;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class OperatingHoursValidatorTest {

    @Test
    void 캠퍼스_운영시간이_아니면_예외() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 13, 0, 0);
        assertThatThrownBy(() -> OperatingHoursValidator.validate(dateTime))
                .isInstanceOf(IllegalArgumentException.class);
    }
}