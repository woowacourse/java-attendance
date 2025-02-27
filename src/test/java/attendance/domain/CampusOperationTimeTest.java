package attendance.domain;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CampusOperationTimeTest {

    @ParameterizedTest
    @CsvSource(value = {
            "2025, 2, 27, 7, 59, false",
            "2025, 2, 27, 8, 0, true",
            "2025, 2, 27, 22, 59, true",
            "2025, 2, 27, 23, 0, false"
    })
    void 캠퍼스_운영_테스트(int year, int month, int day, int hour, int minute, boolean expectedResult) {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour,minute);

        //when & then
        Assertions.assertThat(CampusOperationTime.isOperation(localDateTime.getHour())).isEqualTo(expectedResult);
    }
}
