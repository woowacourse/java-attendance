package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ExpulsionCandidatesTest {

    @Test
    @DisplayName("제적 후보자들을 위험도 순으로 정렬할 수 있다")
    void shouldOrderCandidatesByExpulsionRiskLevel() {
        // given
        AttendanceStatistics student1 = new AttendanceStatistics("1", 5, 3, 2); // 위험도 9
        AttendanceStatistics student2 = new AttendanceStatistics("2", 4, 2, 5); // 위험도 17
        AttendanceStatistics student3 = new AttendanceStatistics("3", 6, 1, 1); // 위험도 4

        List<AttendanceStatistics> statisticsList = List.of(student1, student2, student3);
        ExpulsionCandidates candidates = ExpulsionCandidates.from(statisticsList);

        // when
        ExpulsionCandidates sortedCandidates = candidates.orderByExpulsionRiskLevel();

        // then
        List<AttendanceStatistics> sortedList = sortedCandidates.attendanceStatistics();

        assertThat(sortedList).hasSize(3);
        assertThat(sortedList.get(0)).isEqualTo(student2);
        assertThat(sortedList.get(1)).isEqualTo(student1);
        assertThat(sortedList.get(2)).isEqualTo(student3);

        assertThat(sortedList.get(0).getExpulsionRiskLevel()).isEqualTo(17);
        assertThat(sortedList.get(1).getExpulsionRiskLevel()).isEqualTo(9);
        assertThat(sortedList.get(2).getExpulsionRiskLevel()).isEqualTo(4);
    }
}