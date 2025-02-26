package attendance.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("출결 상태 테스트")
class AttendanceStateTest {

    @Test
    @DisplayName("등교 시작 시간으로부터 5분 초과는 지각이다")
    void lateIfMoreThan5MinutesAfterStartTime() {
        // given
        int overTime = 6;

        // when
        AttendanceState result = AttendanceState.evaluate(overTime);

        // then
        Assertions.assertThat(result)
                .isEqualTo(AttendanceState.TARDY);
    }
}
