import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmptyAttendanceTest {
    @DisplayName("무단 결석에 대해서 시간 기록을 조회하는 경우 예외가 발생한다.")
    @Test
    void test1() {
        // given
        EmptyAttendance attendance = EmptyAttendance.of(LocalDate.of(2025, 2, 28));

        // when & then
        assertThatThrownBy(attendance::getTime).isInstanceOf(RuntimeException.class);
    }
}
