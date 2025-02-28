package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PenaltyStatusTest {
    @Test
    @DisplayName("경고 대상자 상태를 정상적을 반환")
    void determineWarningTest() {
        //given
        int lateCount = 0;
        int absenceCount = 2;
        //when
        PenaltyStatus status = PenaltyStatus.determinePenalty(lateCount, absenceCount);
        //then
        assertThat(status).isEqualTo(PenaltyStatus.WARNING);
    }

    @Test
    @DisplayName("면담 대상자 상태를 정상적으로 반환")
    void determineInterviewTest() {
        //given
        int lateCount = 0;
        int absenceCount = 3;
        //when
        PenaltyStatus status = PenaltyStatus.determinePenalty(lateCount, absenceCount);
        //then
        assertThat(status).isEqualTo(PenaltyStatus.INTERVIEW);
    }

    @Test
    @DisplayName("제적 대상자 상태를 정상적으로 반환")
    void determineExpulsionTest() {
        //given
        int lateCount = 0;
        int absenceCount = 6;
        //when
        PenaltyStatus status = PenaltyStatus.determinePenalty(lateCount, absenceCount);
        //then
        assertThat(status).isEqualTo(PenaltyStatus.EXPULSION);
    }

    @Test
    @DisplayName("지각 3회를 결석 1회로 변환 테스트")
    void convertLateToAbsenceTest() {
        //given
        int lateCount = 3;
        int absenceCount = 2;
        //when
        PenaltyStatus status = PenaltyStatus.determinePenalty(lateCount, absenceCount);
        //then
        assertThat(status).isEqualTo(PenaltyStatus.INTERVIEW);
    }
}