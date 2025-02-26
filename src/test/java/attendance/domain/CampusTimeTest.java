package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CampusTimeTest {

    @Test
    @DisplayName("캠퍼스 운영 시간이 아닌 경우 예외가 발생한다")
    void 캠퍼스_운영_시간이_아닌_경우_예외가_발생한다() {
        // given
        LocalTime time = LocalTime.MAX;

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> CampusTime.validateOperateTime(time))
                .withMessage("[ERROR] 캠퍼스 운영 시간은 08:00 ~ 23:00 입니다.");
    }
}
