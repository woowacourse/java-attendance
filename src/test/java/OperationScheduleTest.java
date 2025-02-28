import static org.assertj.core.api.Assertions.assertThat;

import domain.OperationSchedule;
import java.time.LocalDateTime;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class OperationScheduleTest {

    @ParameterizedTest
    @CsvSource({
            "2024, 12, 2, 8, 0, true",
            "2024, 12, 2, 7, 59, false",
            "2024, 12, 2, 23, 0, true",
            "2024, 12, 2, 23, 1, false"
    })
    void 특정_시간이_캠퍼스_운영시간인지_아닌지_판단한다(int year, int month, int date, int hour, int minute, boolean expected) {
        //given
        LocalDateTime time = LocalDateTime.of(year, month, date, hour, minute);
        //when
        boolean isInOperationTIme = OperationSchedule.isInOperationTime(time);
        //then
        assertThat(isInOperationTIme).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 10, 0, 0, 0, 0",
            "3, 10, 0, 0, 1, 1",
            "3, 9, 59, 59, 999999999, -1"
    })
    void 특정시간과_출석인정시간의_차이를_계산한다(int date, int hour, int minute, int second, int nano, long expected) {
        //given
        LocalDateTime time = LocalDateTime.of(2024, 12, date, hour, minute, second, nano);
        //when
        long difference = OperationSchedule.calculateDifferenceFromStartTime(time);
        //then
        assertThat(difference).isEqualTo(expected);
    }
}
