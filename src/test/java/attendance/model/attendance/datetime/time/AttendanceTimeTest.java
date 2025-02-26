package attendance.model.attendance.datetime.time;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.campus.CampusOperationPolicy;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTimeTest {

    private final CampusOperationPolicy campusOperationPolicy = new CampusOperationPolicy();

    @Test
    void policyApplied() {
        // Given
        final LocalTime time = LocalTime.of(13, 5);

        // When
        final AttendanceTime attendanceTime = AttendanceTime.policyApplied(time, campusOperationPolicy);

        // Then
        assertThat(attendanceTime.getValue()).hasValue(time);
    }

    @DisplayName("값이 null 인 자신을 생성한다.")
    @Test
    void policyApplied_null() {

        // When
        final AttendanceTime attendanceTime = AttendanceTime.nullObject();

        // Then
        assertThat(attendanceTime.getValue()).isNotPresent();
    }

    @DisplayName("LocalTime 을 받아 해당 LocalTime 이 자신의 value 보다 이전인지 반환한다.")
    @Test
    void isBefore() {
        // Given
        final LocalTime time = LocalTime.of(13, 5);
        final AttendanceTime attendanceTime = AttendanceTime.policyApplied(time, campusOperationPolicy);

        // When & Then
        assertThat(attendanceTime.isBefore(LocalTime.of(13, 6))).isTrue();
    }

    @DisplayName("자신의 value 가 비어 있는 null 한 시간인지 반환한다.")
    @Test
    void isNullObject() {
        // Given
        final AttendanceTime attendanceTime = AttendanceTime.nullObject();

        // When & Then
        assertThat(attendanceTime.isNullObject()).isTrue();
    }
}
