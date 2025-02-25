package attendance.model.attendance.datetime.time;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.campus.CampusOperationPolicy;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class AttendanceTimeTest {

    //### AttendanceTime (출석 시간 Class)
    //
    //- **상태**
    //    - LocalTime 출석 시간
    //- **생성**
    //    - LocalTime 과 CampusOperationPolicy 를 받아 생성된다.
    //    - CampusOperationPolicy 를 받아 value 가 null 인 자신을 생성한다.
    //- **동작**
    //    - 생성 시 CampusOperationPolicy 를 통해 캠퍼스 운영 시간인지 검증한다.
    //    - LocalTime 을 받아 해당 LocalTime 이 자신의 value 보다 이전인지 반환한다.
    //    - value 가 null 일수 있으므로, getter 는 Optional 로 반환한다.

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
}
