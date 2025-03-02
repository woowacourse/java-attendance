package domain;

import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CampusTimePolicyTest {
    
    @Nested
    @DisplayName("예외 테스트")
    class Fail {
        @Test
        @DisplayName("캠퍼스 운영 시간이 아니라면 예외가 발생한다")
        void validateCampusTime_test() {
            // given
            LocalTime early = LocalTime.of(7, 59);
            LocalTime over = LocalTime.of(23, 1);

            // when & then
            SoftAssertions.assertSoftly(softAssertions -> {
                Assertions.assertThatThrownBy(() -> {
                    CampusTimePolicy.validateCampusTime(early);
                }).isInstanceOf(IllegalArgumentException.class);
                Assertions.assertThatThrownBy(() -> {
                    CampusTimePolicy.validateCampusTime(over);
                }).isInstanceOf(IllegalArgumentException.class);
            });
        }
    }
}