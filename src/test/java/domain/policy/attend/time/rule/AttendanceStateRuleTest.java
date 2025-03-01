package domain.policy.attend.time.rule;

import domain.policy.AttendanceStateRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceStateRuleTest {

    @ParameterizedTest
    @DisplayName("5분 이하 지각은 출석으로 처리된다.")
    @ValueSource(ints = {-100, -30, -10, -5, 0, 2, 4})
    void lateLessThan5Minutes_thenAttendance(int lateMinutes) {
        assertThat(AttendanceStateRule.decisionState(lateMinutes)).isEqualTo(AttendanceStateRule.ATTEND);
    }

    @ParameterizedTest
    @DisplayName("5분 초과, 30분 이하는 지각으로 처리된다.")
    @ValueSource(ints = {6, 17, 29})
    void lateMoreThan5AndLessThan30Minutes_thenLate(int lateMinutes) {
        assertThat(AttendanceStateRule.decisionState(lateMinutes)).isEqualTo(AttendanceStateRule.LATE);
    }

    @ParameterizedTest
    @DisplayName("30분 초과는 결석으로 처리된다.")
    @ValueSource(ints = {31, 45, 100})
    void lateMoreThan30Minutes_thenAbsent(int lateMinutes) {
        assertThat(AttendanceStateRule.decisionState(lateMinutes)).isEqualTo(AttendanceStateRule.ABSENT);
    }
}
