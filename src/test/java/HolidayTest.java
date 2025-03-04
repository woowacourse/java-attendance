import domain.Holiday;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class HolidayTest {

    @ParameterizedTest
    @CsvSource(value = {"2024-12-25,true", "2024-12-02,false"})
    @DisplayName("대상 날짜가 공휴일인지 확인한다")
    void checkDateIsHoliday(LocalDate date, boolean expected) {
        //when
        boolean actual = Holiday.isHoliday(date);

        //then
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
