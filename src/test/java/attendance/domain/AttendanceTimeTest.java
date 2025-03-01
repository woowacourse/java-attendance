package attendance.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDateTime;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceTimeTest {

    @ParameterizedTest
    @CsvSource(value = {
            "2025, 2, 24, 12, 59", "2025, 2, 24, 13, 6", "2025, 2, 24, 13, 31",
            "2025, 2, 25, 9, 59", "2025, 2, 25, 10, 6", "2025, 2, 25, 10, 31",
            "2025, 2, 26, 9, 59", "2025, 2, 26, 10, 6", "2025, 2, 26, 10, 31",
            "2025, 2, 27, 9, 59", "2025, 2, 27, 10, 6", "2025, 2, 27, 10, 31",
            "2025, 2, 28, 9, 59", "2025, 2, 28, 10, 6", "2025, 2, 28, 10, 31"})
    void 출석_시간_생성(int year, int month, int day, int hour, int minute) {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute);

        //when & then
        assertDoesNotThrow(() -> new AttendanceTime(localDateTime));
    }
    //TODO : 출석에 따른 출성 상태 확인
}
