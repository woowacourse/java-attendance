import domain.Attendance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendanceTest {
    @Test
    @DisplayName("이미 출석한 경우를 확인할 수 있다")
    public void isAlreadyAttendTest() {
        //given
        Attendance attendance = new Attendance("링크", LocalDate.of(2024,12,10), LocalTime.of(10,0));

        //when-then
        assertThat(attendance.isAlreadyAttendance("링크", LocalDate.of(2024,12,10))).isTrue();
    }
}
