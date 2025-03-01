package domain;

import static domain.policy.AttendanceState.ATTENDANCE;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    @Test
    @DisplayName("이미 출석한 경우를 확인할 수 있다")
    public void isAlreadyAttendTest() {
        //given
        Attendance attendance = new Attendance("링크", LocalDate.of(2024, 12, 10), LocalTime.of(10, 0), ATTENDANCE);

        //when-then
        assertThat(attendance.isAttendanceExist("링크", LocalDate.of(2024, 12, 10))).isTrue();
    }
}
