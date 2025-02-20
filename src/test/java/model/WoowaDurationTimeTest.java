package model;

import attendance.model.CustomLocalDateTime;
import attendance.model.WoowaDurationTime;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class WoowaDurationTimeTest {

    @Test
    void 현재_시간이_출석_가능_시작_시간부터_몇분_차이나는지_확인한다() {
        LocalDateTime localDateTime = CustomLocalDateTime.now();
        Assertions.assertThat(WoowaDurationTime.calculateDuration(localDateTime)).isEqualTo(-60);
    }
}
