package attendance.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CampusOperationTimeTest {

    @ParameterizedTest
    @CsvSource(value = {
            "2025, 2, 27, 8, 0",
            "2025, 2, 27, 22, 59",
    })
    void 캠퍼스_운영_테스트(int year, int month, int day, int hour, int minute) {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute);

        //when & then
        assertDoesNotThrow(() -> CampusOperationTime.isOperation(localDateTime.getHour()));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2025, 2, 27, 7, 59",
            "2025, 2, 27, 23, 1",
    })
    void 캠퍼스_운영_안하는_시간_테스트(int year, int month, int day, int hour, int minute) {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute);

        //when & then
        Assertions.assertThatThrownBy(() -> CampusOperationTime.isOperation(localDateTime.getHour()))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.NOT_OPEN_CAMPUS.getMessage());
    }
}
