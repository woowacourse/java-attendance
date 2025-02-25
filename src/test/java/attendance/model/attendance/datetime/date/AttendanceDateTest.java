package attendance.model.attendance.datetime.date;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.campus.CampusOperationPolicy;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class AttendanceDateTest {

    private final CampusOperationPolicy campusOperationPolicy = new CampusOperationPolicy();

    @Test
    void isSameDate() {
        // Given
        final LocalDate date = LocalDate.of(2024, 12, 2);
        final AttendanceDate attendanceDate = AttendanceDate.policyApplied(date, campusOperationPolicy);

        // When & Then
        assertThat(attendanceDate.isSameDate(date)).isTrue();
    }

    @Test
    void isSameDayOfWeek() {

        // Given
        final LocalDate date = LocalDate.of(2024, 12, 2);
        final AttendanceDate attendanceDate = AttendanceDate.policyApplied(date, campusOperationPolicy);

        // When & Then
        assertThat(attendanceDate.isSameDayOfWeek(date.getDayOfWeek())).isTrue();
    }
}
