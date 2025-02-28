package attendance;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OperatingTimeTest {

    @DisplayName("운영시간인 08:00일 경우, true를 반환한다")
    @Test
    void validate_is_operating_time() {
        LocalTime validateTime = LocalTime.of(8, 0);
        boolean isOperating = OperatingTime.isOperating(validateTime);
        assertThat(isOperating).isTrue();
    }

}
