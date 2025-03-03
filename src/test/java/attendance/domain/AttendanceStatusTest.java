package attendance.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceStatusTest {

    @DisplayName("지각한 시간이 5분 이하면 출석이다")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 5})
    void test_present() {
        // given
        int lateTime = 5;

        // when
        AttendanceStatus status = AttendanceStatus.from(lateTime);

        // then
        Assertions.assertThat(status).isEqualTo(AttendanceStatus.PRESENT);
    }

    @DisplayName("지각한 시간이 5분 초과 30분 이하면 지각이다")
    @ParameterizedTest
    @ValueSource(ints = {6, 30})
    void test_late() {
        // given
        int lateTime = 6;

        // when
        AttendanceStatus status = AttendanceStatus.from(lateTime);

        // then
        Assertions.assertThat(status).isEqualTo(AttendanceStatus.LATE);
    }

    @DisplayName("지각한 시간이 30분 초과면 결석이다")
    @Test
    void test_absent() {
        // given
        int lateTime = 31;

        // when
        AttendanceStatus status = AttendanceStatus.from(lateTime);

        // then
        Assertions.assertThat(status).isEqualTo(AttendanceStatus.ABSENT);
    }

}
