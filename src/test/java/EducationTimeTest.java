import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class EducationTimeTest {

    @ParameterizedTest
    @CsvSource(value = {"2024-12-02,13:00", "2024-12-03,10:00"})
    @DisplayName("요일에 따른 교육 시작 시간 판정 기능")
    void checkEducationStartTimeUsingDayOfWeek(LocalDate targetDate, LocalTime expected) {
        //when
        LocalTime actual = EducationTime.getEducationStartTime(targetDate);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"2024-12-01", "2024-12-25"})
    @DisplayName("운영일이 아닌 날을 기반으로 교육 시작 시간을 판정할 시 예외 처리")
    void throwExceptionWhenInputNotOperationDate(LocalDate targetDate) {
        //when & then
        assertThatThrownBy(() -> EducationTime.getEducationStartTime(targetDate));
    }
}
