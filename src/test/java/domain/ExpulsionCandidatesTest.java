package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ExpulsionCandidatesTest {

    @Test
    @DisplayName("제적 후보자들을 위험도 순으로 정렬한 후, 닉네임 순으로 정렬할 수 있다.")
    void shouldOrderCandidatesByExpulsionRiskLevel() {
        // given
        AttendanceStatistics student1 = new AttendanceStatistics("1", 0, 3, 2); // 위험도 9
        AttendanceStatistics student2 = new AttendanceStatistics("2", 0, 2, 5); // 위험도 17
        AttendanceStatistics student3 = new AttendanceStatistics("3", 0, 1, 1); // 위험도 4
        AttendanceStatistics student4 = new AttendanceStatistics("4", 0, 2, 5); // 위험도 17
        AttendanceStatistics student5 = new AttendanceStatistics("5", 0, 1, 1); // 위험도 4

        List<AttendanceStatistics> statisticsList = List.of(student1, student2, student3, student4, student5);
        ExpulsionCandidates candidates = ExpulsionCandidates.from(statisticsList);

        // when
        ExpulsionCandidates sortedCandidates = candidates.orderByExpulsionRiskLevelAndNickname();

        // then
        List<AttendanceStatistics> sortedList = sortedCandidates.attendanceStatistics();


        assertAll(
                () -> assertThat(sortedList).hasSize(5),
                () -> assertThat(sortedList.get(0)).isEqualTo(student2),
                () -> assertThat(sortedList.get(1)).isEqualTo(student4),
                () -> assertThat(sortedList.get(2)).isEqualTo(student1),
                () -> assertThat(sortedList.get(3)).isEqualTo(student3),
                () -> assertThat(sortedList.get(4)).isEqualTo(student5),

                () -> assertThat(sortedList.get(0).getExpulsionRiskLevel()).isEqualTo(17),
                () -> assertThat(sortedList.get(1).getExpulsionRiskLevel()).isEqualTo(17),
                () -> assertThat(sortedList.get(2).getExpulsionRiskLevel()).isEqualTo(9),
                () -> assertThat(sortedList.get(3).getExpulsionRiskLevel()).isEqualTo(4),
                () -> assertThat(sortedList.get(4).getExpulsionRiskLevel()).isEqualTo(4),

                () -> assertThat(sortedList.get(0).nickname()).isEqualTo("2"),
                () -> assertThat(sortedList.get(1).nickname()).isEqualTo("4"),
                () -> assertThat(sortedList.get(2).nickname()).isEqualTo("1"),
                () -> assertThat(sortedList.get(3).nickname()).isEqualTo("3"),
                () -> assertThat(sortedList.get(4).nickname()).isEqualTo("5"));
    }
}
