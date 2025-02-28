import domain.AttendTime;
import domain.AttendTimes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendTimesTest {
    @DisplayName("시간 타임라인을 반환 할 수 있다")
    @Test
    void test1(){
        AttendTimes attendTimes = new AttendTimes();
        AttendTime attendTime = new AttendTime(LocalDate.of(2024,12,16), LocalTime.of(12,12));
        attendTimes.add(attendTime);
        assertThat(attendTimes.getAttendTimeline().size()).isEqualTo(10);
    }

    @DisplayName("출석한 횟수를 계산 할 수 있다")
    @Test
    void test2(){
        AttendTimes attendTimes = new AttendTimes();
        AttendTime attendTime = new AttendTime(LocalDate.of(2024,12,17), LocalTime.of(10,12));
        AttendTime attendTime2 = new AttendTime(LocalDate.of(2024,12,17), LocalTime.of(9,12));
        attendTimes.add(attendTime);
        attendTimes.add(attendTime2);
        assertThat(attendTimes.calculateAttendedCount()).isEqualTo(1);
    }
    @DisplayName("지각한 횟수를 계산 할 수 있다")
    @Test
    void test3(){
        AttendTimes attendTimes = new AttendTimes();
        AttendTime attendTime = new AttendTime(LocalDate.of(2024,12,3), LocalTime.of(10,12));
        AttendTime attendTime2 = new AttendTime(LocalDate.of(2024,12,4), LocalTime.of(9,12));
        attendTimes.add(attendTime);
        attendTimes.add(attendTime2);
        assertThat(attendTimes.calculateLateCount()).isEqualTo(1);
    }
    @DisplayName("지각한 횟수를 계산 할 수 있다")
    @Test
    void test4(){
        AttendTimes attendTimes = new AttendTimes();
        AttendTime attendTime = new AttendTime(LocalDate.of(2024,12,11), LocalTime.of(10,12));
        AttendTime attendTime2 = new AttendTime(LocalDate.of(2024,12,12), LocalTime.of(9,12));
        attendTimes.add(attendTime);
        attendTimes.add(attendTime2);
        assertThat(attendTimes.calculateAbsentCount()).isEqualTo(8);
    }
}
