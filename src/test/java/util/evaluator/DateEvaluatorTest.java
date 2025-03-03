package util.evaluator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class DateEvaluatorTest {

    @ParameterizedTest
    @CsvSource({"1,false", "25,false", "2,true"})
    @DisplayName("운영 날짜 판별 기능 테스트")
    void 운영_날짜_판별_기능_테스트(int day, boolean isOpenDate) {
        // given
        LocalDate date = LocalDate.of(2024, 12, day);
        // when & then
        assertEquals(isOpenDate, DateEvaluator.isOpenDate(date));
    }
}
