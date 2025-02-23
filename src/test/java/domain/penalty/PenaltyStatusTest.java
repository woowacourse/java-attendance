package domain.penalty;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PenaltyStatusTest {

    @Test
    @DisplayName("크루원의 결석이 2회 미만일 때 정상임을 반환")
    void normalCrewTest() {
        int absenceCount = 1;
        int lateCount = 2;

        PenaltyStatus status = PenaltyStatus.getPenaltyStatus(absenceCount, lateCount);

        assertThat(status).isEqualTo(PenaltyStatus.NONE);
    }


    @Test
    @DisplayName("크루원의 결석이 2회 이상일 때 경고 대상자임을 반환")
    void warningCrewTest() {
        int absenceCount = 2;
        int lateCount = 0;

        PenaltyStatus status = PenaltyStatus.getPenaltyStatus(absenceCount, lateCount);

        assertThat(status).isEqualTo(PenaltyStatus.WARNING);
    }

    @Test
    @DisplayName("크루원의 결석이 3회 이상일 때 면담 대상자임을 반환")
    void intervieweeCrewTest() {
        int absenceCount = 2;
        int lateCount = 3;

        PenaltyStatus status = PenaltyStatus.getPenaltyStatus(absenceCount, lateCount);

        assertThat(status).isEqualTo(PenaltyStatus.INTERVIEWEE);
    }

    @Test
    @DisplayName("크루원의 결석이 5회 초과일 때 제적 대상자임을 반환")
    void expulsionCrewTest() {
        int absenceCount = 5;
        int lateCount = 3;

        PenaltyStatus status = PenaltyStatus.getPenaltyStatus(absenceCount, lateCount);

        assertThat(status).isEqualTo(PenaltyStatus.EXPULSION);
    }
}
