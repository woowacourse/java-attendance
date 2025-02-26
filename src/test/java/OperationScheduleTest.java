import domain.OperationSchedule;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
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
        Assertions.assertThat(isInOperationTIme).isEqualTo(expected);
    }
}
