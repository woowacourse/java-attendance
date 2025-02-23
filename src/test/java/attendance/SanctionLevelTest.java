package attendance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SanctionLevelTest {

    @Test
    @DisplayName("모든 크루원의 제재 통계를 출력한다.")
    void test_SanctionStatistics() {
        //given&when

        //then
    }

    @Test
    @DisplayName("제재 통계가 제재 수준 순으로 정렬된다.")
    void test_orderBySanctionLevel() {
        // 제적 위험자는 제적 대상자, 면담 대상자, 경고 대상자순으로 출력한다.
        //given&when

        //then
    }

    @Test
    @DisplayName("제재 수준이 같을 경우, 출석 상태 통계를 내림차순으로 정렬한다.")
    void test_orderByAttendanceStateStatisticsDescending() {
        //given&when

        //then
    }

    @Test
    @DisplayName("출석 상태 통계가 같을 경우, 닉네임을 오름차순을 정렬한다.")
    void test_orderByNickNameAscending() {
        //given&when

        //then
    }
}
