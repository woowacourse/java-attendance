import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceDateTimeTest {

    @Test
    @DisplayName("AttendanceTime 을 통해 오늘이 주말인지 공휴일인지 확인하는 메서드 테스트")
    void test1() {
        AttendanceTime saturdayAttendanceTime = new AttendanceTime();
        AttendanceTime sundayAttendanceTime = new AttendanceTime();
        AttendanceTime christmasAttendanceTime = new AttendanceTime();
        Assertions.assertTrue(attendanceTime.toLocalDate.dayOfWeek.equals.(DayOfWeek.SUNDAY));
        Assertions.assertTrue(attendanceTime.toLocalDate.dayOfWeek.equals.(DayOfWeek.SATURDAY));
        Assertions.assertTrue(attendanceTime.toLocalDate.dayOfMonth == 25);
    }

    @Test
    @DisplayName("AttendanceTime 멤버 변수를 LocalDate 로 바꾸는 메서드 테스트")
    void test2() {
        AttendanceTime attendanceTime = new AttendanceTime(LocalDateTime.of(2024, 12, 12, 12, 0));
        Assertions.assertTrue(
                attendanceTime.toLocalDate.equals(LocalTime.of(12, 0))
        );
    }

    @Test
    @DisplayName("AttendanceTime 멤버 변수를 LocalTime 으로 바꾸는 메서드 테스트")
    void test3() {
        AttendanceTime attendanceTime = new AttendanceTime(LocalDateTime.of(2024, 12, 12, 12, 0));
        Assertions.assertTrue(
                attendanceTime.toLocalDate.equals(LocalDate.of(2024, 12, 12))
        );
    }

    @Test
    @DisplayName("AttendanceTime 의 멤버 변수를 LocalTime 으로 바꾸고, 08:00 ~ 23:00 사이에 있는지 확인하는 메서드")
    void test4() {
        AttendanceTime attendanceTime = new AttendanceTime(LocalDateTime.of(2024, 12, 12, 7, 0));
        Assertions.assertTrue(
                attendanceTime.toLocalTime.isNotOpeningTime()
        );
    }

    @Test
    @DisplayName("AttendanceTime 의 멤버 변수를 LocalTime 으로 바꾸고, 08:00 ~ 23:00 사이에 있는지 확인하는 메서드")
    void test4() {
        AttendanceTime attendanceTime = new AttendanceTime(LocalDateTime.of(2024, 12, 12, 23, 10));
        Assertions.assertTrue(
                attendanceTime.toLocalTime.isNotOpeningTime()
        );
    }
}
