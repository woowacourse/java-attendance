import java.time.LocalTime;
import model.AttendanceCalculatorByDay;
import model.AttendanceStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceCalculatorByDayTest {

    @Test
    @DisplayName("요일과 시간을 기준으로 model.AttendanceStatus 객체 생성 테스트 (월요일 출석)")
    void test1() {
        Assertions.assertThat(AttendanceCalculatorByDay.
                attendanceCalculator(1, LocalTime.of(13,0))).
                isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    @DisplayName("요일과 시간을 기준으로 model.AttendanceStatus 객체 생성 테스트 (금요일 결석)")
    void test2() {
        Assertions.assertThat(AttendanceCalculatorByDay.
                attendanceCalculator(5, LocalTime.of(10,31))).
                isEqualTo(AttendanceStatus.ABSENT);
    }

    @Test
    @DisplayName("정수형을 입력 받고, String 형 요일을 출력하는 메서드 테스트")
    void test3() {
        int friday = 5;
        Assertions.assertThat(AttendanceCalculatorByDay.
                        findDayByDayOfWeekValue(friday)).
                isEqualTo("금요일");
    }



}