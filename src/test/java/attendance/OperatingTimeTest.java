package attendance;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OperatingTimeTest {

    @DisplayName("주어진 시간이 08:00일 경우, true를 반환한다")
    @Test
    void given_8_then_return_true() {
        LocalTime validateTime = LocalTime.of(8, 0);
        boolean isOperating = OperatingTime.isOperating(validateTime);
        assertThat(isOperating).isTrue();
    }

    @DisplayName("주어진 시간이 23:00일 경우, true를 반환한다")
    @Test
    void given_23_then_return_true() {
        LocalTime validateTime = LocalTime.of(8, 0);
        boolean isOperating = OperatingTime.isOperating(validateTime);
        assertThat(isOperating).isTrue();
    }

    @DisplayName("주어진 시간이 07:59일 경우, false를 반환한다")
    @Test
    void given_7_59_then_return_false() {
        LocalTime validateTime = LocalTime.of(7, 59);
        boolean isOperating = OperatingTime.isOperating(validateTime);
        assertThat(isOperating).isFalse();
    }

    @DisplayName("운영시간인 23:01일 경우, false를 반환한다")
    @Test
    void given_23_01_then_return_false() {
        LocalTime validateTime = LocalTime.of(23, 1);
        boolean isOperating = OperatingTime.isOperating(validateTime);
        assertThat(isOperating).isFalse();
    }

}
