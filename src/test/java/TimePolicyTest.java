import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("시간 정책 테스트")
public class TimePolicyTest {

    @Test
    @DisplayName("시의 범위를 벗어나면 예외가 발생한다")
    void validateHourTest(){
        //given
        String hour = "24";

        //when-then
        assertThatThrownBy(() -> TimePolicy.validateHour(hour))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
