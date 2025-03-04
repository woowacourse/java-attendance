package domain.dateTime;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceDateValidatorTest {

    @DisplayName("주말또는 공휴일이면 예외 처리 (24년 12월 기준)")
    @ParameterizedTest
    @ValueSource(ints = {14, 15, 21, 25})
    void validateWeekend(final int input) {
        // given
        final LocalDate weekend = LocalDate.of(2024, 12, input);

        // when
        // then
        assertThatThrownBy(() -> AttendanceDateValidator.validate(weekend))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("평일이면 에러가 발생하지 않는다")
    @ParameterizedTest
    @ValueSource(ints = {13, 24, 26, 30})
    void passDate(final int input) {
        // given
        final LocalDate weekend = LocalDate.of(2024, 12, input);

        // when
        // then
        assertThatCode(() -> AttendanceDateValidator.validate(weekend))
                .doesNotThrowAnyException();
    }
}
