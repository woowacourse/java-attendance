import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceCalculatorByDayTest {

    @Test
    @DisplayName("요일과 시간을 기준으로 AttendanceStatus 객체 생성 테스트 (월요일 출석)")
    void test1() {
        Assertions.assertThat(AttendanceCalculatorByDay.
                attendanceCalculator(1, LocalTime.of(13,0))).
                isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    @DisplayName("요일과 시간을 기준으로 AttendanceStatus 객체 생성 테스트 (금요일 결석)")
    void test2() {
        Assertions.assertThat(AttendanceCalculatorByDay.
                attendanceCalculator(5, LocalTime.of(10,31))).
                isEqualTo(AttendanceStatus.ABSENT);
    }



}