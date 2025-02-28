package domain;

import domain.policy.AttendanceStateRule;
import domain.policy.absent.AbsentRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceCountsTest {

    @Test
    @DisplayName("초기화 시 모든 출석 상태의 개수는 0이어야 한다.")
    void whenInitializeReturnAllValuesAreZero() {
        // given
        Nickname nickname = new Nickname("강산");
        AttendanceCounts attendanceCounts = AttendanceCounts.initialize(nickname);

        // when
        // then
        assertAll(
                () -> assertThat(attendanceCounts.getCount(AttendanceStateRule.ATTEND)).isZero(),
                () -> assertThat(attendanceCounts.getCount(AttendanceStateRule.LATE)).isZero(),
                () -> assertThat(attendanceCounts.getCount(AttendanceStateRule.ABSENT)).isZero()
        );
    }

    @Test
    @DisplayName("출석 상태 증가 시 해당 상태의 카운트가 증가해야 한다.")
    void whenIncrementShouldPlusOne() {
        // given
        Nickname nickname = new Nickname("강산");
        AttendanceCounts attendanceCounts = AttendanceCounts.initialize(nickname);

        // when
        // then
        attendanceCounts.increment(AttendanceStateRule.ATTEND);
        assertThat(attendanceCounts.getCount(AttendanceStateRule.ATTEND)).isEqualTo(1);

        attendanceCounts.increment(AttendanceStateRule.ATTEND);
        assertThat(attendanceCounts.getCount(AttendanceStateRule.ATTEND)).isEqualTo(2);
    }

    @Test
    @DisplayName("지각이 일정 기준을 넘으면 결석 개수에 반영해야 한다.")
    void testAdjustedAbsentCount() {
        // given
        Nickname nickname = new Nickname("강산");
        AttendanceCounts attendanceCounts = AttendanceCounts.initialize(nickname);
        int lateCount = 8;

        // when
        for (int i = 0; i < lateCount; i++) {
            attendanceCounts.increment(AttendanceStateRule.LATE);
        }

        // then
        assertThat(attendanceCounts.getAdjustedAbsentCount()).isEqualTo(lateCount / AbsentRule.LATE_TO_ABSENT_RATIO);
    }

    @Test
    @DisplayName("제적 위험도 계산이 올바르게 수행되어야 한다.")
    void whenGivenLateAndAbsentCountShouldCalcExpectedRiskLevel() {
        // given
        Nickname nickname = new Nickname("강산");
        AttendanceCounts attendanceCounts = AttendanceCounts.initialize(nickname);

        int lateCount = 4;
        for (int i = 0; i < lateCount; i++) {
            attendanceCounts.increment(AttendanceStateRule.LATE);
        }

        int absentCount = 2;
        for (int i = 0; i < absentCount; i++) {
            attendanceCounts.increment(AttendanceStateRule.ABSENT);
        }

        // when
        int expectedRiskLevel = (absentCount * AbsentRule.LATE_TO_ABSENT_RATIO) + lateCount;

        // then
        assertThat(attendanceCounts.getExpulsionRiskLevel()).isEqualTo(expectedRiskLevel);
    }

    @Test
    @DisplayName("닉네임을 가져올 때, 올바르게 설정되어 있어야 한다.")
    void whenInitializeShouldSetCorrectNickname() {
        // given
        Nickname nickname = new Nickname("강산");
        AttendanceCounts attendanceCounts = AttendanceCounts.initialize(nickname);

        // when
        // then
        assertThat(attendanceCounts.getNickname()).isEqualTo(nickname);
    }
}