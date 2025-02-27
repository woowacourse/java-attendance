package attendance.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("출결 위험도 테스트")
class AttendanceRiskTest {

    @Test
    @DisplayName("결석 2회 이상은 경고 대상자이다")
    void 결석_2회_이상은_경고_대상자이다() {
        // given
        int absence = 2;

        // when
        AttendanceRisk result = AttendanceRisk.evaluate(absence);

        // then
        Assertions.assertThat(result).isEqualTo(AttendanceRisk.WARNING);
    }
}
