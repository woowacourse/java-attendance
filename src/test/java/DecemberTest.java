import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.December;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DecemberTest {

    @DisplayName("주말이 아닌 날을 입력하면 예외를 발생시킨다.")
    @Test
    void test1() {
        assertThatThrownBy(() -> December.checkWeekday(LocalDateTime.of(2024, 12, 15, 0, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
