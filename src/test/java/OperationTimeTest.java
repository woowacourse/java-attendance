import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.OperationTime;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class OperationTimeTest {

    @ParameterizedTest
    @CsvSource(value = {"2024-12-01", "2024-12-07", "2024-12-25"})
    @DisplayName("주어진 날짜를 기반으로 운영일이 아니면 예외를 발생하는 기능")
    void checkDateIsWeekend(LocalDate targetDate) {//when & then
        assertThatThrownBy(() -> OperationTime.checkIsOperationDate(targetDate));
    }

    @ParameterizedTest
    @CsvSource(value = {"07:59,false", "08:00,true", "23:00,true", "23:01,false"})
    @DisplayName("주어진 시간이 운영 시간에 포함되는지 판정하는 기능")
    void checkTimeIsContainsOperationTime(LocalTime targetTime, boolean expected) {
        //when
        boolean actual = OperationTime.isContainsOperationTime(targetTime);

        //then
        assertThat(actual).isEqualTo(expected);
    }
}
