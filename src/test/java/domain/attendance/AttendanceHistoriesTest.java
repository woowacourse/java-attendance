package domain.attendance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.crew.Crew;
import domain.date.AttendanceDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttendanceHistoriesTest {

    private AttendanceHistories histories;
    private Crew crewA;
    private Crew crewB;

    private AttendanceDateTime day1Morning;
    private AttendanceDateTime day2Morning;

    @BeforeEach
    void setUp() {
        histories = new AttendanceHistories();
        crewA = Crew.from("CrewA");
        crewB = Crew.from("CrewB");

        day1Morning = AttendanceDateTime.of(1, 9, 0);  // 1일 오전 9시
        day2Morning = AttendanceDateTime.of(2, 9, 0);  // 2일 오전 9시
    }

    @Test
    void addAndFindAllHistoriesOfTest() {
        histories.add(crewA, day1Morning);
        histories.add(crewB, day2Morning);

        List<AttendanceHistory> crewAHistories = histories.findAllHistoriesOf(crewA);
        List<AttendanceHistory> crewBHistories = histories.findAllHistoriesOf(crewB);

        assertThat(crewAHistories).hasSize(1);
        assertThat(crewBHistories).hasSize(1);
    }

    @Test
    void updateHistoryTest() {
        histories.add(crewA, day1Morning);
        AttendanceHistory originalHistory = histories.findAllHistoriesOf(crewA).get(0);

        AttendanceDateTime updatedDateTime = AttendanceDateTime.of(1, 10, 0);
        AttendanceHistory updatedHistory = AttendanceHistory.of(crewA, updatedDateTime);

        histories.update(originalHistory, updatedHistory);

        AttendanceHistory foundHistory = histories.findHistoryBy(updatedHistory);
        assertThat(foundHistory).isEqualTo(updatedHistory);
    }

    @Test
    void findHistoryByThrowsExceptionWhenNotFoundTest() {
        AttendanceHistory nonExistentHistory = AttendanceHistory.of(crewA, day1Morning);
        assertThatThrownBy(() -> histories.findHistoryBy(nonExistentHistory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 출석 기록이 존재하지 않습니다.");
    }

    @Test
    void findHistoriesBeforeTest() {
        AttendanceDateTime day1 = AttendanceDateTime.of(1, 9, 0);
        AttendanceDateTime day2 = AttendanceDateTime.of(2, 9, 0);
        AttendanceDateTime day3 = AttendanceDateTime.of(3, 9, 0);

        histories.add(crewA, day1);
        histories.add(crewA, day2);
        histories.add(crewA, day3);
        histories.add(crewB, day1);

        List<AttendanceHistory> pastHistories = histories.findHistoriesBefore(crewA, 3);
        assertThat(pastHistories).hasSize(2);
    }
}
