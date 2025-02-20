package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    @Test
    void 제적_위험자가_아닌_경우를_확인한다() {
        assertThat(RiskStatus.getRiskStatus(0, 0))
                .isEqualTo(RiskStatus.NONE);
        assertThat(RiskStatus.getRiskStatus(0, 5))
                .isEqualTo(RiskStatus.NONE);
        assertThat(RiskStatus.getRiskStatus(1, 2))
                .isEqualTo(RiskStatus.NONE);

    }

    @Test
    void 경고_대상자인_경우를_확인한다() {
        assertThat(RiskStatus.getRiskStatus(0, 6))
                .isEqualTo(RiskStatus.WARNING);
        assertThat(RiskStatus.getRiskStatus(1, 5))
                .isEqualTo(RiskStatus.WARNING);
        assertThat(RiskStatus.getRiskStatus(2, 0))
                .isEqualTo(RiskStatus.WARNING);
    }

    @Test
    void 면담_대상자인_경우를_확인한다() {
        assertThat(RiskStatus.getRiskStatus(3, 1))
                .isEqualTo(RiskStatus.COUNSELING);
        assertThat(RiskStatus.getRiskStatus(2, 5))
                .isEqualTo(RiskStatus.COUNSELING);
        assertThat(RiskStatus.getRiskStatus(1, 7))
                .isEqualTo(RiskStatus.COUNSELING);
    }

    @Test
    void 제적_대상자인_경우를_확인한다() {
        assertThat(RiskStatus.getRiskStatus(5, 3))
                .isEqualTo(RiskStatus.EXPULSION);
        assertThat(RiskStatus.getRiskStatus(6, 2))
                .isEqualTo(RiskStatus.EXPULSION);
        assertThat(RiskStatus.getRiskStatus(4, 7))
                .isEqualTo(RiskStatus.EXPULSION);
    }

    @Test
    void 크루의_제적_위험_여부를_확인한다() {
        Crew crew = new Crew("시소");

        for(int day = 3; day <= 6; day ++) {
            crew.addAttendStatus(LocalDateTime.of(2024, 12, day, 10, 35, 0));
        }

        assertThat(crew.calculateRiskStatus()).isEqualTo(RiskStatus.EXPULSION);
    }
}