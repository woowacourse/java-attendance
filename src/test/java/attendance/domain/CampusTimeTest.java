package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalTime;

import static attendance.domain.CampusTime.END_TIME;
import static attendance.domain.CampusTime.START_TIME;
import static attendance.domain.CampusTime.validateOperationTime;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CampusTimeTest {

    @ParameterizedTest
    @CsvSource({
            "07:59:00",
            "23:01:00",
    })
    @DisplayName("캠퍼스 운영시간이 아니라면 예외를 발생한다.")
    void 캠퍼스_운영시간이_아니라면_예외를_발생한다(LocalTime target) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> validateOperationTime(target))
                .withMessage(String.format("[ERROR] 캠퍼스 운영시간이 아닙니다. 운영시간은 %s ~ %s 입니다.",
                        START_TIME.getTime(),
                        END_TIME.getTime()
                ));
    }
}
