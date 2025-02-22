package attendance.model;

import static attendance.error.ErrorMessage.ERROR_NOT_WOOWA_OPEN;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class WoowaDurationTimeTest {

    @Test
    void 현재_시간이_출석_가능_시작_시간부터_몇분_차이나는지_확인한다() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 11, 0);

        //when
        long duration = WoowaDurationTime.calculateDuration(localDateTime);

        //then
        Assertions.assertThat(duration).isEqualTo(60);
    }

    @Test
    void 차이를_계산하려는_날짜가_운영시간이_아니면_예외가_발생한다() {
        //given
        LocalDateTime notOpenDateTime = LocalDateTime.of(2024, 12, 1, 10, 0);

        //when & then
        assertThatThrownBy(() -> WoowaDurationTime.calculateDuration(notOpenDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ERROR_NOT_WOOWA_OPEN);
    }

}
