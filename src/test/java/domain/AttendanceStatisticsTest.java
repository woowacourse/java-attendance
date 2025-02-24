package domain;

import domain.rule.AbsentRule;
import domain.rule.AttendanceStateRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceStatisticsTest {

    @Test
    @DisplayName("출석 통계를 생성할 수 있다")
    void createAttendanceStatistics() {
        // given
        String nickname = "강산";
        int absentCount = 2;
        int lateCount = 3;
        Map<AttendanceStateRule, Integer> format = AttendanceStatistics.getFormat();
        format.put(AttendanceStateRule.ATTEND, 0);
        format.put(AttendanceStateRule.LATE, lateCount);
        format.put(AttendanceStateRule.ABSENT, absentCount);

        // when
        AttendanceStatistics statistics = AttendanceStatistics.from(nickname, format);

        // then
        assertThat(statistics).isNotNull();
        assertThat(statistics.nickname()).isEqualTo(nickname);
        assertThat(statistics.attendCount()).isEqualTo(0);
        assertThat(statistics.lateCount()).isEqualTo(lateCount);
        assertThat(statistics.absentCount()).isEqualTo(absentCount);
    }

    @Test
    @DisplayName("결석 조정 횟수를 계산할 수 있다")
    void calculateAdjustedAbsentCount() {
        // given
        int absentCount = 2;
        int lateCount = 3;
        AttendanceStatistics statistics = new AttendanceStatistics("강산", 0, lateCount, absentCount);

        // when
        int adjustedAbsentCount = statistics.getAdjustedAbsentCount();

        // then
        int expectedAdjustedAbsentCount = absentCount + (lateCount / AbsentRule.LATE_TO_ABSENT_RATIO);
        assertThat(adjustedAbsentCount).isEqualTo(expectedAdjustedAbsentCount);
    }

    @Test
    @DisplayName("제적 위험도를 계산할 수 있다")
    void calculateExpulsionRiskLevel() {
        // given
        int absentCount = 2;
        int lateCount = 3;
        AttendanceStatistics statistics = new AttendanceStatistics("강산", 0, lateCount, absentCount);

        // when
        int expulsionRiskLevel = statistics.getExpulsionRiskLevel();

        // then
        int expectedRiskLevel = (absentCount * AbsentRule.LATE_TO_ABSENT_RATIO) + lateCount;
        assertThat(expulsionRiskLevel).isEqualTo(expectedRiskLevel);
    }

    @Test
    @DisplayName("초기 통계 포맷을 가져올 수 있다")
    void getInitialFormat() {
        // when
        Map<AttendanceStateRule, Integer> format = AttendanceStatistics.getFormat();

        // then
        assertThat(format).isNotNull();
        assertThat(format.size()).isEqualTo(AttendanceStateRule.values().length);
        assertThat(format.values()).allMatch(value -> value == 0);
    }
}
