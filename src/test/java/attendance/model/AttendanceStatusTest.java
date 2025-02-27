package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.model.AttendanceStatus;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceStatusTest {

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
    void determineAttendanceStatusTest(final LocalDateTime time, final String attendanceStatusName) {
        // Given

        // When
        AttendanceStatus attendanceStatus = AttendanceStatus.from(time);

        // Then
        assertThat(attendanceStatus.name()).isEqualTo(attendanceStatusName);
    }
}
