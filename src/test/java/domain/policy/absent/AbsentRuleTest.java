package domain.policy.absent;

import domain.AttendanceCounts;
import domain.Nickname;
import domain.policy.AttendanceStateRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AbsentRuleTest {

    @Test
    @DisplayName("출석 카운트 기록을 통해서 결석 정책을 계산할 수 있다.")
    void canCalculateAbsentPolicyByAttendanceCounts() {
        // given
        AttendanceCounts attendanceCounts1 = AttendanceCounts.initialize(Nickname.from("강산"));
        AttendanceCounts attendanceCounts2 = AttendanceCounts.initialize(Nickname.from("띠용"));
        AttendanceCounts attendanceCounts3 = AttendanceCounts.initialize(Nickname.from("폰트"));
        AttendanceCounts attendanceCounts4 = AttendanceCounts.initialize(Nickname.from("칼리"));

        for (int i = 0; i < 2; i++) {
            attendanceCounts2.increment(AttendanceStateRule.ABSENT);
        }

        for (int i = 0; i < 3; i++) {
            attendanceCounts3.increment(AttendanceStateRule.ABSENT);
        }

        for (int i = 0; i < 6; i++) {
            attendanceCounts4.increment(AttendanceStateRule.ABSENT);
        }

        // when
        // then
        assertAll(
                () -> assertThat(AbsentRule.calculateAbsentPolicy(attendanceCounts1)).isEqualTo(AbsentRule.NONE),
                () -> assertThat(AbsentRule.calculateAbsentPolicy(attendanceCounts2)).isEqualTo(AbsentRule.WARNING),
                () -> assertThat(AbsentRule.calculateAbsentPolicy(attendanceCounts3)).isEqualTo(AbsentRule.INTERVIEW),
                () -> assertThat(AbsentRule.calculateAbsentPolicy(attendanceCounts4)).isEqualTo(AbsentRule.EXPULSION));

    }

    @Test
    @DisplayName("제적 위험자를 확인할 수 있다.")
    void isRiskOfExpulsion() {
        // given
        AttendanceCounts attendanceCounts1 = AttendanceCounts.initialize(Nickname.from("강산"));
        AttendanceCounts attendanceCounts2 = AttendanceCounts.initialize(Nickname.from("띠용"));
        AttendanceCounts attendanceCounts3 = AttendanceCounts.initialize(Nickname.from("폰트"));
        AttendanceCounts attendanceCounts4 = AttendanceCounts.initialize(Nickname.from("칼리"));

        for (int i = 0; i < 2; i++) {
            attendanceCounts2.increment(AttendanceStateRule.ABSENT);
        }

        for (int i = 0; i < 3; i++) {
            attendanceCounts3.increment(AttendanceStateRule.ABSENT);
        }

        for (int i = 0; i < 6; i++) {
            attendanceCounts4.increment(AttendanceStateRule.ABSENT);
        }

        // when
        // then
        assertAll(
                () -> assertThat(AbsentRule.isRiskOfExpulsion(attendanceCounts1)).isFalse(),
                () -> assertThat(AbsentRule.isRiskOfExpulsion(attendanceCounts2)).isTrue(),
                () -> assertThat(AbsentRule.isRiskOfExpulsion(attendanceCounts3)).isTrue(),
                () -> assertThat(AbsentRule.isRiskOfExpulsion(attendanceCounts4)).isTrue());
    }
}
