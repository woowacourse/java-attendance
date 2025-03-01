package domain;

import static constants.TestDataMaker.HOLIDAY_DATE;
import static constants.TestDataMaker.MONDAY_DATE;
import static constants.TestDataMaker.SATURDAY_DATE;
import static constants.TestDataMaker.SUNDAY_DATE;
import static constants.TestDataMaker.WEDNESDAY_DATE;
import static domain.DecemberCalendar.HOLIDAY;
import static domain.DecemberCalendar.WORKING_DAY;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DecemberCalendarTest {
    @Test
    @DisplayName("날짜를 입력받아 날짜에 해당하는 값을 반환한다.")
    void Judge_Working_Day() {
        assertThat(DecemberCalendar.judgeWorkingDay(SUNDAY_DATE)).isEqualTo("일요일");
        assertThat(DecemberCalendar.judgeWorkingDay(MONDAY_DATE)).isEqualTo("월요일");
        assertThat(DecemberCalendar.judgeWorkingDay(WEDNESDAY_DATE)).isEqualTo(WORKING_DAY);
        assertThat(DecemberCalendar.judgeWorkingDay(SATURDAY_DATE)).isEqualTo("토요일");
        assertThat(DecemberCalendar.judgeWorkingDay(HOLIDAY_DATE)).isEqualTo(HOLIDAY);
    }
}