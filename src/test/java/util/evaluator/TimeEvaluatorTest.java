package util.evaluator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TimeEvaluatorTest {

    @ParameterizedTest
    @CsvSource({"07:30,false", "23:30,false", "12:00,true"})
    @DisplayName("운영 시간 판별 기능 테스트")
    void 운영_시간_판별_기능_테스트(LocalTime time, boolean isOpenTime) {
        // given & when & then
        assertEquals(isOpenTime, TimeEvaluator.isOpenTime(time));
    }
}
