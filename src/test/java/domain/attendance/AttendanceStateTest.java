package domain.attendance;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceStateTest {
    @DisplayName("출석시간 5분 이내에 출석하면 출석이다")
    @Test
    void test() {
        AttendanceState attendanceState = AttendanceState.calculateAttendanceState(1,
                LocalDateTime.of(2025, 2, 17, 13, 5));

        Assertions.assertThat(attendanceState.getState()).isEqualTo("출석");
    }

    @DisplayName("출석시간 5분 초과 30분 이내에 출석하면 지각이다")
    @ParameterizedTest
    @ValueSource(ints = {6, 30})
    void test2(int minute) {
        AttendanceState attendanceState = AttendanceState.calculateAttendanceState(1,
                LocalDateTime.of(2025, 2, 17, 13, minute));

        Assertions.assertThat(attendanceState.getState()).isEqualTo("지각");
    }

    @DisplayName("출석시간 30분 초과 후 출석하면 결석이다")
    @Test
    void test3() {
        AttendanceState attendanceState = AttendanceState.calculateAttendanceState(1,
                LocalDateTime.of(2025, 2, 17, 13, 31));

        Assertions.assertThat(attendanceState.getState()).isEqualTo("결석");
    }
}