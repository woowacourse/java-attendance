package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttendanceCounterTest {

    private AttendanceCounter attendanceCounter;

    @BeforeEach
    void setUp() {
        Map<AttendanceState, Integer> counts = new HashMap<>(
                Map.of(AttendanceState.ATTENDANCE, 2,
                        AttendanceState.TARDINESS, 3,
                        AttendanceState.ABSENCE, 1)
        );
        attendanceCounter = new AttendanceCounter(counts);
    }

    @Test
    void 횟수를_증가시킨다() {
        // Given
        AttendanceState attendanceState = AttendanceState.ATTENDANCE;

        // When
        attendanceCounter.increase(attendanceState);

        // Then
        assertThat(attendanceCounter.getCount(attendanceState)).isEqualTo(3);
    }

    @Test
    void 횟수를_반환한다() {
        assertAll(
                () -> assertThat(attendanceCounter.getCount(AttendanceState.ATTENDANCE)).isEqualTo(2),
                () -> assertThat(attendanceCounter.getCount(AttendanceState.TARDINESS)).isEqualTo(3),
                () -> assertThat(attendanceCounter.getCount(AttendanceState.ABSENCE)).isEqualTo(1)
        );
    }
}
