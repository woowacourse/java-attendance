package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OperatingTimeTest {

    @DisplayName("8시가 주어졌을 경우, 운영시간이기에 true를 반환해야 한다")
    @Test
    void check_operating_8_time() {
        LocalTime compareTime = LocalTime.of(8, 0);
        boolean isOperating = OperatingTime.isOperate(compareTime);
        assertThat(isOperating).isTrue();
    }

    @DisplayName("23시가 주어졌을 경우, 운영시간이기에 true를 반환해야 한다")
    @Test
    void check_operating_23_time() {
        LocalTime compareTime = LocalTime.of(23, 0);
        boolean isOperating = OperatingTime.isOperate(compareTime);
        assertThat(isOperating).isTrue();
    }

    @DisplayName("7시 59분이 주어졌을 경우, 운영시간이 아니기에 false를 반환해야 한다")
    @Test
    void check_operating_7_59_time() {
        LocalTime compareTime = LocalTime.of(7, 59);
        boolean isOperating = OperatingTime.isOperate(compareTime);
        assertThat(isOperating).isFalse();
    }

    @DisplayName("23시 01분이 주어졌을 경우, 운영시간이 아니기에 false를 반환해야 한다")
    @Test
    void check_operating_23_01_time() {
        LocalTime compareTime = LocalTime.of(23, 1);
        boolean isOperating = OperatingTime.isOperate(compareTime);
        assertThat(isOperating).isFalse();
    }
}
