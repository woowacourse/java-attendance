package attendance.model.attendance.datetime.date;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.campus.CampusOperationPolicy;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class AttendanceDateTest {

    //- **상태**
    //    - LocalDate 출석 일자
    //- **생성**
    //    - LocalDate 와 CampusOperationPolicy 를 받아 생성된다.
    //- **동작**
    //    - 생성 시 CampusOperationPolicy 를 통해 캠퍼스 운영 날짜인지 검증한다.
    //    - LocalDate 를 받아 해당 LocalDate 와 자신이 같은 날인지 반환한다.
    //    - DayOfWeek 를 받아 해당 요일이 자신의 요일인지 반환한다.

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
