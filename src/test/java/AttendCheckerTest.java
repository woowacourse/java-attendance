import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.OperationTimeChecker;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendCheckerTest {

    @ParameterizedTest
    @CsvSource(value = {"2024-12-01", "2024-12-07", "2024-12-25"})
    @DisplayName("주어진 날짜를 기반으로 운영일이 아니면 예외를 발생하는 기능")
    void checkDateIsWeekend(LocalDate targetDate) {
        //given
        OperationTimeChecker operationTimeChecker = new OperationTimeChecker();

        //when & then
        assertThatThrownBy(() -> operationTimeChecker.checkIsOperationDate(targetDate));
    }

    @ParameterizedTest
    @CsvSource(value = {"07:59,false", "08:00,true", "23:00,true", "23:01,false"})
    @DisplayName("주어진 시간이 운영 시간에 포함되는지 판정하는 기능")
    void checkTimeIsContainsOperationTime(LocalTime targetTime, boolean expected) {
        //given
        OperationTimeChecker operationTimeChecker = new OperationTimeChecker();

        //when
        boolean actual = operationTimeChecker.isContainsOperationTime(targetTime);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"2024-12-02,13:00", "2024-12-03,10:00"})
    @DisplayName("요일에 따른 교육 시작 시간 판정 기능")
    void checkEducationStartTimeUsingDayOfWeek(LocalDate targetDate, LocalTime expected) {
        //given
        OperationTimeChecker operationTimeChecker = new OperationTimeChecker();

        //when
        LocalTime actual = operationTimeChecker.getEducationStartTime(targetDate);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("운영일이 아닌 날을 기반으로 교육 시작 시간을 판정할 시 예외 처리")
    void throwExceptionWhenInputNotOperationDate() {
        //given
        OperationTimeChecker operationTimeChecker = new OperationTimeChecker();
        LocalDate weekend = LocalDate.of(2024, 12, 1);
        LocalDate holiday = LocalDate.of(2024, 12, 25);

        //when & then
        assertThatThrownBy(() -> operationTimeChecker.getEducationStartTime(weekend));
        assertThatThrownBy(() -> operationTimeChecker.getEducationStartTime(holiday));
    }
}
