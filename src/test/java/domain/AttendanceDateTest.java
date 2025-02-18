package domain;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceDateTest {
    @DisplayName("휴일일 때 출석일자 생성이 안 된다")
    @Test
    void test() {
        // given
        LocalDateTime christmasDateTime = LocalDateTime.of(2024, 12, 25, 0, 0);

        // when && then
        Assertions.assertThatThrownBy(() -> new AttendanceDate(christmasDateTime))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("");
    }
}
