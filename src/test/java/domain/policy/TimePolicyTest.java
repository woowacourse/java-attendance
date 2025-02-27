package domain.policy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("시간 정책 테스트")
public class TimePolicyTest {
    TimePolicy timePolicy;

    @BeforeEach
    void setUp(){
        timePolicy = new TimePolicy();
    }

    @ParameterizedTest
    @DisplayName("캠퍼스 운영 시간이 아니면 예외가 발생한다")
    @CsvSource({"07:59", "23:01"})
    void validateOperatingTimeTest(LocalTime time) {
        assertThatThrownBy(() -> timePolicy.validateOperatingTime(time))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
