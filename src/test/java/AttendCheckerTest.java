import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendCheckerTest {

    @Test
    @DisplayName("주어진 날짜를 기반으로 주말인지 판정하는 기능")
    void checkDateIsWeekend() {
        //given
        OperationTimeChecker operationTimeChecker = new OperationTimeChecker();

        //when
        LocalDate weekend = LocalDate.of(2024, 12, 1);
        boolean actual = operationTimeChecker.isWeekend(weekend);

        LocalDate weekDay = LocalDate.of(2024, 12, 2);
        boolean actual2 = operationTimeChecker.isWeekend(weekDay);

        LocalDate weekend2 = LocalDate.of(2024, 12, 7);
        boolean actual3 = operationTimeChecker.isWeekend(weekend2);

        //then
        assertThat(actual).isEqualTo(true);
        assertThat(actual2).isEqualTo(false);
        assertThat(actual3).isEqualTo(true);
    }

    @Test
    @DisplayName("주어진 날짜가 공휴일인지 판정하는 기능")
    void checkDateIsHoliday() {
        //given
        OperationTimeChecker operationTimeChecker = new OperationTimeChecker();
        LocalDate christmas = LocalDate.of(2024, 12, 25);
        LocalDate notHoliday = LocalDate.of(2024, 12, 1);

        //when
        boolean actual = operationTimeChecker.isHoliday(christmas);
        boolean actual2 = operationTimeChecker.isHoliday(notHoliday);

        //then
        assertThat(actual).isEqualTo(true);
        assertThat(actual2).isEqualTo(false);
    }

    @Test
    @DisplayName("주어진 시간이 운영 시간에 포함되는지 판정하는 기능")
    void checkTimeIsContainsOperationTime() {
        //given
        OperationTimeChecker operationTimeChecker = new OperationTimeChecker();
        LocalTime innerOperationTime = LocalTime.of(8, 0);
        LocalTime notOperationTime = LocalTime.of(7, 59);

        LocalTime innerOperationTime2 = LocalTime.of(23, 0);
        LocalTime notOperationTime2 = LocalTime.of(23, 1);

        //when
        boolean actual = operationTimeChecker.isContainsOperationTime(innerOperationTime);
        boolean actual2 = operationTimeChecker.isContainsOperationTime(notOperationTime);
        boolean actual3 = operationTimeChecker.isContainsOperationTime(innerOperationTime2);
        boolean actual4 = operationTimeChecker.isContainsOperationTime(notOperationTime2);

        //then
        assertThat(actual).isEqualTo(true);
        assertThat(actual2).isEqualTo(false);
        assertThat(actual3).isEqualTo(true);
        assertThat(actual4).isEqualTo(false);
    }

    @Test
    @DisplayName("요일에 따른 교육 시작 시간 판정 기능")
    void checkEducationStartTimeUsingDayOfWeek() {
        //given
        OperationTimeChecker operationTimeChecker = new OperationTimeChecker();
        LocalDate monday = LocalDate.of(2024, 12, 2);

        //when
        LocalTime actual = operationTimeChecker.getEducationStartTime(monday);

        //then
        assertThat(actual).isEqualTo(LocalTime.of(13, 0));
    }
}
