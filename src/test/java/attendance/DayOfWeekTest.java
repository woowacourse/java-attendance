package attendance;

import static attendance.DayOfWeek.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DayOfWeekTest {

    @DisplayName("2024-12-2가 주어졌을 때, 월요일을 반환해야 한다.")
    @Test
    void given_2024_12_2_then_return_monday() {
        LocalDate findDate = LocalDate.of(2024, 12, 2);
        DayOfWeek dayOfWeek = findDayOfWeek(findDate);
        assertThat(dayOfWeek).isEqualTo(MONDAY);
    }

    @DisplayName("2024-12-3이 주어졌을 때, 화요일을 반환해야 한다.")
    @Test
    void given_2024_12_3_then_return_tuesday() {
        LocalDate findDate = LocalDate.of(2024, 12, 3);
        DayOfWeek dayOfWeek = DayOfWeek.findDayOfWeek(findDate);
        assertThat(dayOfWeek).isEqualTo(TUESDAY);
    }

    @DisplayName("화요일 10:00에 출석했을 경우, 출석을 반환해야 한다")
    @Test
    void given_tuesday_10_then_return_attendance() {
        assertAttendanceType(TUESDAY, LocalTime.of(10, 0), "출석");
    }

    @DisplayName("월요일 13:00에 출석했을 경우, 출석을 반환해야 한다")
    @Test
    void given_monday_13_then_return_attendance() {
        assertAttendanceType(MONDAY, LocalTime.of(13, 0), "출석");
    }

    @DisplayName("월요일 13:05에 출석했을 경우, 지각을 반환해야 한다")
    @Test
    void given_monday_13_05_then_return_attendance() {
        assertAttendanceType(MONDAY, LocalTime.of(13, 5), "지각");
    }

    @DisplayName("월요일 13:30에 출석했을 경우, 결석을 반환해야 한다")
    @Test
    void given_monday_13_30_then_return_attendance() {
        assertAttendanceType(MONDAY, LocalTime.of(13, 30), "결석");
    }

    @DisplayName("캠퍼스 운영시간이 아닌 07:00에 출석 할 경우, 예외가 발생해야 한다.")
    @Test
    void given_attendance_time_07_then_throw_exception() {
        LocalTime attendanceTime = LocalTime.of(7, 0);
        assertThatThrownBy(() -> MONDAY.decideAttendanceType(attendanceTime));
    }

    @DisplayName("캠퍼스 운영시간이 아닌 23:01에 출석 할 경우, 예외가 발생해야 한다.")
    @Test
    void given_attendance_time_23_01_then_throw_exception() {
        LocalTime attendanceTime = LocalTime.of(23, 1);
        assertThatThrownBy(() -> MONDAY.decideAttendanceType(attendanceTime));
    }

    @DisplayName("캠퍼스 운영시간인 08:00에 출석할 경우, 예외가 발생해서는 안 된다.")
    @Test
    void given_attendance_time_08_then_throw_exception() {
        LocalTime attendanceTime = LocalTime.of(8, 0);
        assertThatCode(() -> MONDAY.decideAttendanceType(attendanceTime)).doesNotThrowAnyException();
    }

    @DisplayName("캠퍼스 운영시간인 23:00에 출석할 경우, 예외가 발생해서는 안 된다.")
    @Test
    void given_attendance_time_23_then_throw_exception() {
        LocalTime attendanceTime = LocalTime.of(23, 0);
        assertThatCode(() -> MONDAY.decideAttendanceType(attendanceTime)).doesNotThrowAnyException();
    }

    private void assertAttendanceType(DayOfWeek dayOfWeek, LocalTime attendanceTime, String expectedResult) {
        String attendanceType = dayOfWeek.decideAttendanceType(attendanceTime);
        assertThat(attendanceType).isEqualTo(expectedResult);
    }
}
