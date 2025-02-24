import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.December;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DecemberTest {

    @DisplayName("출석부에서 오늘의 날짜를 비교할 수 있다")
    @Test
    void test1() {
        assertThatThrownBy(() -> December.checkWeekday(LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주말의 경우에는 오류를 발생시킬 수 있다")
    @Test
    void test2() {
        assertThatThrownBy(() -> December.checkWeekday(LocalDateTime.of(2024, 12, 15, 0, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
