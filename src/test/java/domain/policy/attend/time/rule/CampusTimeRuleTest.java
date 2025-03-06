package domain.policy.attend.time.rule;

import domain.policy.attend.time.CampusTimeRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CampusTimeRuleTest {

    @Test
    @DisplayName("운영 시작 시간과 운영 종료 시간 사이의 시간은 출석 가능하다.")
    void betweenOpenAndCloseCanAttend() {
        // given
        LocalTime openTime = LocalTime.of(8, 0);
        LocalTime midTime = LocalTime.of(12, 30);
        LocalTime closeTime = LocalTime.of(23, 0);

        // when
        // then
        assertAll(
                () -> assertThat(CampusTimeRule.canAttendTime(openTime)).isTrue(),
                () -> assertThat(CampusTimeRule.canAttendTime(midTime)).isTrue(),
                () -> assertThat(CampusTimeRule.canAttendTime(closeTime)).isTrue()
        );

    }

    @Test
    @DisplayName("운영 시작 시간 이전 또는 운영 종료 시간 이후에는 출석이 불가능하다.")
    void outsideCampusHoursCannotAttend() {
        // given
        LocalTime beforeOpenTime = LocalTime.of(7, 59);
        LocalTime afterCloseTime = LocalTime.of(23, 1);

        // when
        // then
        assertAll(
                () -> assertThat(CampusTimeRule.canAttendTime(beforeOpenTime)).isFalse(),
                () -> assertThat(CampusTimeRule.canAttendTime(afterCloseTime)).isFalse()
        );
    }
}
