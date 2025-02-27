package domain.policy;

import view.Policy.TimePolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("시간 정책 테스트")
public class TimePolicyTest {
    TimePolicy timePolicy;

    @BeforeEach
    void setUp(){
        timePolicy = new TimePolicy();
    }

    @ParameterizedTest
    @DisplayName("시의 범위를 벗어나면 예외가 발생한다")
    @ValueSource(strings = {"-1", "24", "a"})
    void validateHourTest(String hour){
        assertThatThrownBy(() -> timePolicy.validateHour(hour))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("분의 범위를 벗어나면 예외가 발생한다")
    @ValueSource(strings = {"60", "-1", "a"})
    void validateMinuteTest(String minute) {
        assertThatThrownBy(() -> timePolicy.validateMinute(minute))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("시간 입력 형식이 다르면 예외가 발생한다")
    @ValueSource(strings = {"12/34", "12:12:12"})
    void validateTimeFormatTest(String time) {
        assertThatThrownBy(() -> timePolicy.validateTimeFormat(time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("캠퍼스 운영 시간이 아니면 예외가 발생한다")
    @ValueSource(strings = {"07:59", "23:01"})
    void validateOperatingTimeTest(String time) {
        assertThatThrownBy(() -> timePolicy.validateOperatingTime(time))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
