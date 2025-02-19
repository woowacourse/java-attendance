package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalenderTest {

    @DisplayName("특정 날짜의 요일을 반환한다.")
    @Test
    void getDayOfWeekByDayOfMonth() {
        //given
        int dayOfMonth = 19;

        //when
        String result = Calender.findBy(dayOfMonth);

        //then
        assertThat(result).isEqualTo("목요일");
    }
}
