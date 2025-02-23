package domain.rule;

import domain.AttendanceStatistics;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AbsentRuleTest {

    @Test
    @DisplayName("결석 정책을 계산할 수 있다.")
    void calculateAbsentPolicy() {
        // given
        AttendanceStatistics attendanceStatistics1 = new AttendanceStatistics("강산", 0, 3, 0);
        AttendanceStatistics attendanceStatistics2 = new AttendanceStatistics("강산", 0, 3, 1);
        AttendanceStatistics attendanceStatistics3 = new AttendanceStatistics("강산", 0, 3, 2);
        AttendanceStatistics attendanceStatistics6 = new AttendanceStatistics("강산", 0, 3, 5);

        // when
        // then
        assertAll(
                () -> assertThat(AbsentRule.calculateAbsentPolicy(attendanceStatistics1)).isEqualTo(AbsentRule.NONE),
                () -> assertThat(AbsentRule.calculateAbsentPolicy(attendanceStatistics2)).isEqualTo(AbsentRule.WARNING),
                () -> assertThat(AbsentRule.calculateAbsentPolicy(attendanceStatistics3)).isEqualTo(AbsentRule.INTERVIEW),
                () -> assertThat(AbsentRule.calculateAbsentPolicy(attendanceStatistics6)).isEqualTo(AbsentRule.EXPULSION));
    }

    @Test
    @DisplayName("제적 위험자를 확인할 수 있다.")
    void isRiskOfExpulsion() {
        // given
        // when
        // then
        assertAll(
                () -> assertThat(AbsentRule.NONE.isRiskOfExpulsion()).isFalse(),
                () -> assertThat(AbsentRule.WARNING.isRiskOfExpulsion()).isTrue(),
                () -> assertThat(AbsentRule.INTERVIEW.isRiskOfExpulsion()).isTrue(),
                () -> assertThat(AbsentRule.EXPULSION.isRiskOfExpulsion()).isFalse());
    }
}
