package domain;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

class RiskStatusTest {

    @Test
    void 제적_위험도를_판별한다() {
        final int dismissalTardyCount = 3;
        final int dismissalAbsenceCount = 5;

        final int counsellingTardyCount = 4;
        final int counsellingAbsenceCount = 3;

        final int warningTardyCount = 1;
        final int warningAbsenceCount = 2;

        final int noneTardyCount = 1;
        final int noneAbsenceCount = 1;

        assertThat(RiskStatus.evaluateStatus(dismissalTardyCount, dismissalAbsenceCount)).isEqualTo(RiskStatus.DISMISSAL);
        assertThat(RiskStatus.evaluateStatus(counsellingTardyCount, counsellingAbsenceCount)).isEqualTo(RiskStatus.COUNSELLING);
        assertThat(RiskStatus.evaluateStatus(warningTardyCount, warningAbsenceCount)).isEqualTo(RiskStatus.WARNING);
        assertThat(RiskStatus.evaluateStatus(noneTardyCount, noneAbsenceCount)).isEqualTo(RiskStatus.NONE);
    }
}