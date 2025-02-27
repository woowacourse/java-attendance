package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.model.AttendanceType;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceTypeTest {

    @DisplayName("출석 타입을 결정한다")
    @ParameterizedTest
    @CsvSource({
            "2024-12-02T13:00, ATTENDANCE",
            "2024-12-03T10:00, ATTENDANCE",
            "2024-12-03T10:05, ATTENDANCE",
            "2024-12-03T10:06, LATE",
            "2024-12-03T10:30, LATE",
            "2024-12-03T13:00, ABSENCE",
            "2024-12-03T09:54, ATTENDANCE"
    })
    void determineAttendanceTypeTest(final LocalDateTime time, final String attendanceTypeName) {
        // Given

        // When
        AttendanceType attendanceType = AttendanceType.from(time);

        // Then
        assertThat(attendanceType.name()).isEqualTo(attendanceTypeName);
    }
}
