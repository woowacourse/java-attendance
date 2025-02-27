package domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("출석 가능한 시간 검증 테스트")
class AttendanceDateTimeValidatorTest {

    @ParameterizedTest
    @CsvSource({
        "2025-02-24T08:00",
        "2025-02-24T23:00",
        "2025-02-28T08:00",
        "2025-02-28T23:00",
    })
    void 날짜와_시간이_출석가능한_시간이면_예외가_발생하지_않는다(LocalDateTime possibleDateTime) {
        assertThatCode(() -> AttendanceDateTimeValidator.validateDateTime(possibleDateTime))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource({
        "2025-02-22T10:00",
        "2025-02-23T10:00",
        "2025-02-24T07:59",
        "2025-02-24T23:01",
        "2025-02-28T07:59",
        "2025-02-28T23:01"
    })
    void 날짜와_시간이_출석가능한_시간이_아니면_예외가_발생한다(LocalDateTime impossibleDateTime) {
        assertThatThrownBy(() -> AttendanceDateTimeValidator.validateDateTime(impossibleDateTime))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
