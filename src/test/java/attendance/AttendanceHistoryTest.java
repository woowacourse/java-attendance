package attendance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {

    @Test
    @DisplayName("닉네임을 입력하면 전날까지의 크루 출석 기록을 확인할 수 있다.")
    void test_attendanceHistory() {
        //given&when

        //then
    }

    @Test
    @DisplayName("등록되지 않은 닉네임을 입력할 경우, 예외가 발생한다.")
    void error_notRegisteredNickname() {
        //given&when

        //then
    }

    @Test
    @DisplayName("해당 크루의 출석 상태 통계를 확인할 수 있다.")
    void test_getAttendanceStateStatistics() {
        //given&when

        //then
    }

    @Test
    @DisplayName("해당 크루의 제재 수준을 판단한다.")
    void test_shouldJudgeSanctionLevelOfCrew() {
        //given&when

        //then
    }

    @Test
    @DisplayName("등교하지 않은 날에 대해서도 출석 기록에 포함한다.")
    void test_shouldIncludeAbsentDaysInAttendanceHistory() {
        //given&when

        //then
    }
}
