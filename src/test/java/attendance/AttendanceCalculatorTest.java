package attendance;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceCalculatorTest {

    @DisplayName("2024-12-2가 주어졌을 때, 월요일을 반환해야 한다.")
    @Test
    void given_2024_12_2_then_return_monday() {
        String expectedDayOfWeek = "월요일";
        LocalDate findDate = LocalDate.of(2024, 12, 2);
        String dayOfWeek = AttendanceCalculator.findDayOfWeek(findDate);
        assertThat(dayOfWeek).isEqualTo(expectedDayOfWeek);
    }

    @DisplayName("2024-12-3이 주어졌을 때, 화요일을 반환해야 한다.")
    @Test
    void given_2024_12_3_then_return_tuesday() {
        String expectedDayOfWeek = "화요일";
        LocalDate findDate = LocalDate.of(2024, 12, 3);
        String dayOfWeek = AttendanceCalculator.findDayOfWeek(findDate);
        assertThat(dayOfWeek).isEqualTo(expectedDayOfWeek);
    }

    @DisplayName("출석 시간 10:00이 주어졌을 경우, 출석을 반환한다")
    @Test
    void given_10_then_return_attendance() {
        LocalTime attendanceTime = LocalTime.of(10, 0);
        String attendanceType = AttendanceCalculator.decideAttendanceType("화요일", attendanceTime);
        assertThat(attendanceType).isEqualTo("출석");
    }

    @DisplayName("출석 시간 10:05가 주어졌을 경우, 지각을 반환한다")
    @Test
    void given_10_5_then_return_attendance() {
        LocalTime attendanceTime = LocalTime.of(10, 5);
        String attendanceType = AttendanceCalculator.decideAttendanceType("화요일", attendanceTime);
        assertThat(attendanceType).isEqualTo("지각");
    }

    @DisplayName("출석 시간 10:30이 주어졌을 경우, 결석을 반환한다")
    @Test
    void given_10_30_then_return_attendance() {
        LocalTime attendanceTime = LocalTime.of(10, 30);
        String attendanceType = AttendanceCalculator.decideAttendanceType("화요일", attendanceTime);
        assertThat(attendanceType).isEqualTo("결석");
    }

    @DisplayName("화요일 10:00에 출석했을 경우, 출석을 반환해야 한다")
    @Test
    void given_tuesday_10_then_return_attendance() {
        LocalTime attendanceTime = LocalTime.of(10, 0);
        String attendanceType = AttendanceCalculator.decideAttendanceType("화요일", attendanceTime);
        assertThat(attendanceType).isEqualTo("출석");
    }

    @DisplayName("월요일 13:00에 출석했을 경우, 출석을 반환해야 한다")
    @Test
    void given_monday_13_then_return_attendance() {
        LocalTime attendanceTime = LocalTime.of(13, 0);
        String attendanceType = AttendanceCalculator.decideAttendanceType("월요일", attendanceTime);
        assertThat(attendanceType).isEqualTo("출석");
    }

}
