import java.time.LocalDate;
import org.assertj.core.api.Assertions;
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
        Assertions.assertThat(actual).isEqualTo(true);
        Assertions.assertThat(actual2).isEqualTo(false);
        Assertions.assertThat(actual3).isEqualTo(true);
    }
}
