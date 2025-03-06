package domain;

import exception.AppException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CheckInTimeTest {
    @Test
    @DisplayName("출석 시간이면 CheckInTime을 정상적으로 생성")
    void createChekInTimeTest() {
        //given
        LocalTime checkInTime = LocalTime.of(10, 0);
        //when
        //then
        assertThatNoException().isThrownBy(() -> CheckInTime.of(checkInTime));
    }

    @Test
    @DisplayName("출석 시간이 아닐 경우 예외 발생")
    void nonClassTimeTest() {
        //given
        LocalTime checkInTime = LocalTime.of(23, 10);
        //when
        //then
        assertThatThrownBy(() -> CheckInTime.of(checkInTime))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(AppException.PREFIX);
    }
}