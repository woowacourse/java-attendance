package util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HolidayManagerTest {

    @Test
    void 공휴일_확인_처리() {
        // given
        int dayOfMonth = 25;

        // when
        boolean result = HolidayManager.isHoliday(dayOfMonth);

        // then
        Assertions.assertThat(result).isTrue();
    }

}