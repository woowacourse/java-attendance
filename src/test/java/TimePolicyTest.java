import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("시간 정책 테스트")
public class TimePolicyTest {

    @ParameterizedTest
    @DisplayName("시의 범위를 벗어나면 예외가 발생한다")
    @ValueSource(strings = {"-1", "24", "a"})
    void validateHourTest(String hour){
        assertThatThrownBy(() -> TimePolicy.validateHour(hour))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("분의 범위를 벗어나면 예외가 발생한다")
    @ValueSource(strings = {"60", "-1"})
    void validateMinuteTest(String minute){
        assertThatThrownBy(() -> TimePolicy.validateMinute(minute))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
