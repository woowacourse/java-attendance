package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DangerCrewTest {
    @Test
    @DisplayName("걸석 횟수가 더 많은 크루가 우선순위")
    void absenceCountSortTest() {
        //given
        DangerCrew dangerCrew1 = DangerCrew.of(Crew.of("조로"), 1, 1);
        DangerCrew dangerCrew2 = DangerCrew.of(Crew.of("루피"), 1, 0);
        //when
        //then
        assertThat(dangerCrew1.compareTo(dangerCrew2)).isLessThan(0);
    }

    @Test
    @DisplayName("지각 횟수가 더 많은 크루가 우선순위")
    void lateCountSortTest() {
        //given
        DangerCrew dangerCrew1 = DangerCrew.of(Crew.of("조로"), 1, 1);
        DangerCrew dangerCrew2 = DangerCrew.of(Crew.of("루피"), 2, 1);
        //when
        //then
        assertThat(dangerCrew1.compareTo(dangerCrew2)).isGreaterThan(0);
    }

    @Test
    @DisplayName("지각 및 결석 횟수가 같으면 닉네임을 기준으로 오름차순")
    void nicknameSortTest() {
        //given
        DangerCrew dangerCrew1 = DangerCrew.of(Crew.of("가가"), 1, 1);
        DangerCrew dangerCrew2 = DangerCrew.of(Crew.of("나나"), 1, 1);
        //when
        //then
        assertThat(dangerCrew1.compareTo(dangerCrew2)).isLessThan(0);
    }
}