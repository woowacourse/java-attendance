package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceStateTest {

    @ParameterizedTest
    @CsvSource({
            "0, ATTENDANCE",
            "5, ATTENDANCE",
            "6, TARDINESS",
            "30, TARDINESS",
            "31, ABSENCE"
    })
    void 출석_상태를_확인한다(final int diff, final String expected) {
        // Given

        // When
        AttendanceState attendanceState = AttendanceState.from(diff);

        // Then
        assertThat(attendanceState.name()).isEqualTo(expected);
    }
}
