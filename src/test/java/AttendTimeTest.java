import domain.AttendTime;
import domain.AttendanceStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendTimeTest {
    @DisplayName("출석의 상태를 파악할 수 있다")
    @Test
    void test1(){
        AttendTime attendTime = new AttendTime(LocalDate.of(2024,12,16),LocalTime.of(9,58));
        assertThat(attendTime.checkAttendanceStatus()).isEqualTo(AttendanceStatus.ATTENDED);
    }
    @DisplayName("출석의 상태를 파악할 수 있다")
    @Test
    void test2(){
        AttendTime attendTime = new AttendTime(LocalDate.of(2024,12,16), LocalTime.of(10,58));
        assertThat(attendTime.checkAttendanceStatus()).isEqualTo(AttendanceStatus.ATTENDED);
    }
    @DisplayName("출석의 상태를 파악할 수 있다")
    @Test
    void test3(){
        AttendTime attendTime = new AttendTime(LocalDate.of(2024,12,17),LocalTime.of(10,6));
        assertThat(attendTime.checkAttendanceStatus()).isEqualTo(AttendanceStatus.LATE);
    }
}
