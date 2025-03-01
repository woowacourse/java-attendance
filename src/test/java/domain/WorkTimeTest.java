package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class WorkTimeTest {

    @Test
    void 유효하지_않은_시간를_생성하면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> new WorkTime(24, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시간은 0 이상 23 이하여야 합니다.");

        assertThatThrownBy(() -> new WorkTime(0, 60))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("분은 0 이상 59 이하여야 합니다.");
    }
}
