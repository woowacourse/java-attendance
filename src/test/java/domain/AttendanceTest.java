package domain;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTest {
    @DisplayName("주말 또는 공휴일에 출석하면 예외를 뱉는다")
    @Test
    void test() {
        // given
        LocalDateTime christmas = LocalDateTime.of(2024, 12, 25, 10, 0);
        Attendance attendance = new Attendance(christmas);

        // when & then
        Assertions.assertThatThrownBy(() -> new Attendance(christmas))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말 또는 공휴일에는 출석할 수 없습니다");
    }

}