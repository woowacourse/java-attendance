package attendance.model;

import static attendance.fixture.TestFixture.makeAttendance;
import static attendance.fixture.TestFixture.makeDay;
import static attendance.fixture.TestFixture.makeDefaultTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.domain.model.CrewHistories;
import attendance.domain.model.CrewHistory;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewHistoriesTest {

    private static final String hotteok = "호떡";
    private static final String mint = "밍트";
    private static final String wilson = "윌슨";

    @DisplayName("크루 이름으로 크루를 조회한다")
    @Test
    void findCrewNicknameTest() {
        CrewHistory crewHistory1 = new CrewHistory(new HashMap<>());
        CrewHistory crewHistory2 = new CrewHistory(new HashMap<>());
        // Given
        CrewHistories crewHistories = new CrewHistories(
                Map.of(hotteok, crewHistory1, mint, crewHistory2)
        );

        // When & Then
        assertThat(crewHistories.findCrewByNickname(hotteok)).isEqualTo(crewHistory1);
    }

    @DisplayName("크루가 존재하지 않는다면 예외를 발생시킨다")
    @Test
    void crewNotExistTest() {
        CrewHistory crewHistory1 = new CrewHistory(new HashMap<>());
        CrewHistory crewHistory2 = new CrewHistory(new HashMap<>());
        // Given
        CrewHistories crewHistories = new CrewHistories(
                Map.of(hotteok, crewHistory1, mint, crewHistory2)
        );

        // When & Then
        assertThatThrownBy(() -> crewHistories.findCrewByNickname("사바"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @DisplayName("제적 위험자를 확인한다")
    @Test
    void findDismissalCrewsTest() {
        // Given
        LocalDate todayDate = makeDay(10);
        CrewHistory crewHistory1 = new CrewHistory(Map.of(
                makeDay(2), makeDefaultTime(2),
                makeDay(3), makeDefaultTime(3),
                makeDay(4), makeAttendance(4),
                makeDay(5), makeAttendance(5),
                makeDay(6), makeAttendance(6),
                makeDay(9), makeAttendance(9)
        ));
        CrewHistory crewHistory2 = new CrewHistory(Map.of(
                makeDay(2), makeDefaultTime(2),
                makeDay(3), makeDefaultTime(3),
                makeDay(4), makeDefaultTime(4),
                makeDay(5), makeAttendance(5),
                makeDay(6), makeAttendance(6),
                makeDay(9), makeAttendance(9)
        ));

        CrewHistory crewHistory3 = new CrewHistory(Map.of(
                makeDay(2), makeDefaultTime(2),
                makeDay(3), makeDefaultTime(3),
                makeDay(4), makeDefaultTime(4),
                makeDay(5), makeDefaultTime(5),
                makeDay(6), makeDefaultTime(6),
                makeDay(9), makeDefaultTime(9)
        ));
        CrewHistories crewHistories = new CrewHistories(
                Map.of(hotteok, crewHistory1, mint, crewHistory2, wilson, crewHistory3));

        // When & Then
        assertThat(crewHistories.findDismissalCrews(todayDate)).containsKeys(hotteok, mint, wilson);
    }

}
