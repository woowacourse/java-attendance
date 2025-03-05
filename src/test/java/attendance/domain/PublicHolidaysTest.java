package attendance.domain;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PublicHolidaysTest {

    @ParameterizedTest
    @CsvSource(value = {
            "2025,3,1", "2025,3,2", "2024,12,25"
    })
    void 공휴일_출석_테스트(int year, int month, int day) {
        //given
        LocalDate currentDate = LocalDate.of(year, month, day);

        //when
        Assertions.assertThatThrownBy(() -> PublicHolidays.isPublicHolidays(currentDate))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.NOT_ATTENDANCE_WEEKEND.getMessage());
    }
}
