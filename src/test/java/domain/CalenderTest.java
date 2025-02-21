package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CalenderTest {

    @DisplayName("특정 날짜의 요일을 반환한다.")
    @Test
    void getDayOfWeekByDayOfMonth() {
        // given
        int dayOfMonth = 19;

        // when
        String result = Calender.findBy(dayOfMonth);

        // then
        assertThat(result).isEqualTo("목요일");
    }

    @DisplayName("공휴일에 출석확인을 하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 7, 8, 14, 15, 21, 22, 25, 28, 29})
    void validateHolyDay(int dayOfMonth) {
        // when & then
        assertThatThrownBy(() -> Calender.validateHolyDay(dayOfMonth))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("공휴일에는 출석을 할 수 없습니다.");
    }

    @DisplayName("공휴일이 아닌 날에 출석확인을 할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5, 9, 10, 11, 12, 13, 16, 17, 18, 19, 20, 23, 24, 26, 27, 30, 31})
    void holyDay(int dayOfMonth) {

        // when & then
        assertThatCode(() -> Calender.validateHolyDay(dayOfMonth))
                .doesNotThrowAnyException();
    }
}
