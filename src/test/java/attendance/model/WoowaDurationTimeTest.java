package attendance.model;

import attendance.TestUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class WoowaDurationTimeTest {

    @Test
    void 현재_시간이_출석_가능_시작_시간부터_몇분_차이나는지_확인한다() {
        //given
        WoowaDate testDate = TestUtil.createTestWoowaDate(LocalDate.of(2024, 12, 3));
        LocalTime testTime = LocalTime.of(11, 0);
        //when
        long duration = WoowaDurationTime.calculateDuration(testDate, testTime);

        //then
        Assertions.assertThat(duration).isEqualTo(60);
    }

}
