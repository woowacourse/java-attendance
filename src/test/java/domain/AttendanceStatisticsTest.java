package domain;

import domain.policy.AttendanceStateRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceStatisticsTest {

    @Test
    @DisplayName("제적 위험도 내림차순, 이름 오름차순으로 정렬할 수 있다.")
    void canSortByExpulsionRiskAndName() {
        // given
        AttendanceCounts attendanceCounts1 = AttendanceCounts.initialize(new Nickname("김강산"));
        AttendanceCounts attendanceCounts2 = AttendanceCounts.initialize(new Nickname("딤칼리"));
        AttendanceCounts attendanceCounts3 = AttendanceCounts.initialize(new Nickname("님띠용"));
        AttendanceCounts attendanceCounts4 = AttendanceCounts.initialize(new Nickname("림엠제이"));

        for (int i = 0; i < 10; i++) {
            attendanceCounts1.increment(AttendanceStateRule.ABSENT);
        }

        for (int i = 0; i < 4; i++) {
            attendanceCounts2.increment(AttendanceStateRule.ABSENT);
        }

        for (int i = 0; i < 4; i++) {
            attendanceCounts3.increment(AttendanceStateRule.ABSENT);
        }

        for (int i = 0; i < 2; i++) {
            attendanceCounts4.increment(AttendanceStateRule.ABSENT);
        }


        AttendanceStatistics attendanceStatistics = AttendanceStatistics.from(List.of(
                attendanceCounts1,
                attendanceCounts2,
                attendanceCounts3,
                attendanceCounts4));

        // when
        List<AttendanceCounts> attendanceStatisticsOrderByExpulsionRiskLevelAndNickname =
                attendanceStatistics.orderByExpulsionRiskLevelAndNickname().getAttendanceStatistics();

        // then
        assertAll(
                () -> assertThat(attendanceStatisticsOrderByExpulsionRiskLevelAndNickname.get(0).getNickname().value()).isEqualTo("김강산"),
                () -> assertThat(attendanceStatisticsOrderByExpulsionRiskLevelAndNickname.get(1).getNickname().value()).isEqualTo("님띠용"),
                () -> assertThat(attendanceStatisticsOrderByExpulsionRiskLevelAndNickname.get(2).getNickname().value()).isEqualTo("딤칼리"),
                () -> assertThat(attendanceStatisticsOrderByExpulsionRiskLevelAndNickname.get(3).getNickname().value()).isEqualTo("림엠제이")
        );
    }

}