import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.December;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class DecemberTest {

    @Test
    void test1() {
        assertThatThrownBy(() -> December.checkWeekday(LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void test2() {
        assertThatThrownBy(() -> December.checkWeekday(LocalDateTime.of(2024, 12, 15, 0, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void test3() {
        System.out.println(December.getWeekDays());
    }
}
