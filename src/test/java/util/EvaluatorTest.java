package util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class EvaluatorTest {

    @ParameterizedTest
    @CsvSource({"1,false", "25,false", "2,true"})
    @DisplayName("운영 날짜 판별 기능 테스트")
    void 운영_날짜_판별_기능_테스트(int day, boolean isOpenDate) {
        // given
        LocalDate date = LocalDate.of(2024, 12, day);
        // when & then
        assertEquals(isOpenDate, Evaluator.isOpenDate(date));
    }

    @ParameterizedTest
    @CsvSource({"07:30,false", "23:30,false", "12:00,true"})
    @DisplayName("운영 시간 판별 기능 테스트")
    void 운영_시간_판별_기능_테스트(LocalTime time, boolean isOpenTime) {
        // given & when & then
        assertEquals(isOpenTime, Evaluator.isOpenTime(time));
    }
}
