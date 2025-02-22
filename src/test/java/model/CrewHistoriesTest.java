package model;

import static model.AttendanceType.DEFAULT_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        CrewHistory crewHistory1 = new CrewHistory(hotteok, new HashMap<>());
        CrewHistory crewHistory2 = new CrewHistory(mint, new HashMap<>());
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
        CrewHistory crewHistory1 = new CrewHistory(hotteok, new HashMap<>());
        CrewHistory crewHistory2 = new CrewHistory(mint, new HashMap<>());
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
        LocalDate todayDate = LocalDate.of(2024, 12, 10);
        CrewHistory crewHistory1 = new CrewHistory(hotteok, Map.of(
                2, LocalDateTime.of(LocalDate.of(2024, 12, 2), DEFAULT_TIME),
                3, LocalDateTime.of(LocalDate.of(2024, 12, 3), DEFAULT_TIME),
                4, LocalDateTime.of(2024, 12, 4, 9, 30),
                5, LocalDateTime.of(2024, 12, 5, 9, 30),
                6, LocalDateTime.of(2024, 12, 6, 9, 30),
                9, LocalDateTime.of(2024, 12, 9, 9, 30)
        ));
        CrewHistory crewHistory2 = new CrewHistory(mint, Map.of(
                2, LocalDateTime.of(LocalDate.of(2024, 12, 2), DEFAULT_TIME),
                3, LocalDateTime.of(LocalDate.of(2024, 12, 3), DEFAULT_TIME),
                4, LocalDateTime.of(LocalDate.of(2024, 12, 4), DEFAULT_TIME),
                5, LocalDateTime.of(2024, 12, 5, 9, 30),
                6, LocalDateTime.of(2024, 12, 6, 9, 30),
                9, LocalDateTime.of(2024, 12, 9, 9, 30)
        ));

        CrewHistory crewHistory3 = new CrewHistory(wilson, Map.of(
                2, LocalDateTime.of(LocalDate.of(2024, 12, 2), DEFAULT_TIME),
                3, LocalDateTime.of(LocalDate.of(2024, 12, 3), DEFAULT_TIME),
                4, LocalDateTime.of(LocalDate.of(2024, 12, 4), DEFAULT_TIME),
                5, LocalDateTime.of(LocalDate.of(2024, 12, 5), DEFAULT_TIME),
                6, LocalDateTime.of(LocalDate.of(2024, 12, 6), DEFAULT_TIME),
                9, LocalDateTime.of(LocalDate.of(2024, 12, 9), DEFAULT_TIME)
        ));
        CrewHistories crewHistories = new CrewHistories(Map.of(hotteok, crewHistory1, mint, crewHistory2, wilson, crewHistory3));

        // When & Then
        assertThat(crewHistories.findDismissalCrews(todayDate)).containsOnly(crewHistory1, crewHistory2, crewHistory3);
    }
}
