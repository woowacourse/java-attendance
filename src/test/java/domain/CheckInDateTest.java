package domain;

import exception.AppException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CheckInDateTest {
    @Test
    @DisplayName("주말이 아닐 경우 CheckInDate를 정상적으로 생성")
    void createCheckInDateTest() {
        //given
        //when
        //then
        assertThatNoException().isThrownBy(() -> CheckInDate.of(2024, 12, 3));
    }

    @Test
    @DisplayName("주말에 CheckInDate 생성하려는 경우 예외 발생")
    void weekendCheckInException() {
        //given
        //when
        //then
        assertThatThrownBy(() -> CheckInDate.of(2024, 12, 1))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(AppException.PREFIX);
    }

    @Test
    @DisplayName("공휴일에 CheckInDate 생성하려는 경우 예외 발생")
    void holidayCheckInDateException() {
        //given
        //when
        //then
        assertThatThrownBy(() -> CheckInDate.of(2024, 12, 25))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(AppException.PREFIX);
    }
}