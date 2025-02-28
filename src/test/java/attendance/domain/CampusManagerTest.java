package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class CampusManagerTest {
    @DisplayName("주어진_날짜가_캠퍼스_운영일인지_여부를_반환할_수_있다")
    @CsvSource(value = {"2024-12-26:True", "2024-12-25:False", "2024-12-22:False"}, delimiterString = ":")
    @ParameterizedTest
    void isOperationDate(LocalDate date, boolean expected) {
        //when
        boolean result = CampusManager.isOperationDate(date);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("주어진_시간이_캠퍼스_운영_시간인지_여부를_반환할_수_있다")
    @ValueSource(strings = {"07:59", "23:01"})
    @ParameterizedTest
    void should_ThrowException_WhenTimeIsNotOperationTime(LocalTime time) {
        //when
        //then
        assertThatThrownBy(() -> CampusManager.validateOperationTime(time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간은 매일 08:00 ~ 23:00 입니다.");
    }
}
