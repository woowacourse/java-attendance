package attendance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    @DisplayName("등교 시간이 입력되면 출석")
    void attendance_basic_test1() {
        LocalDate today = LocalDate.of(2024, 12, 3);

        Attendance attendance = new Attendance();
        assertThat(attendance.attend("10:00", today)).hasFieldOrPropertyWithValue("attendanceStatus", "출석");
    }

    @Test
    @DisplayName("5분 초과는 지각이다")
    void attendance_late_test1() {
        LocalDate today = LocalDate.of(2024, 12, 3);

        Attendance attendance = new Attendance();
        assertThat(attendance.attend("10:06", today)).hasFieldOrPropertyWithValue("attendanceStatus","지각");
    }

    @Test
    @DisplayName("30분 초과는 결석이다")
    void attendance_absence_test1() {
        LocalDate today = LocalDate.of(2024, 12, 3);

        Attendance attendance = new Attendance();
        assertThat(attendance.attend("10:31", today)).hasFieldOrPropertyWithValue("attendanceStatus","결석");
    }

    @Test
    @DisplayName("출석 기록이 없으면 결석")
    void attendance_absence_test2() {
        LocalDate today = LocalDate.of(2024, 12, 3);

        Attendance attendance = new Attendance();
        assertThat(attendance.attend("--:--", today)).hasFieldOrPropertyWithValue("attendanceStatus","결석");
    }

    @Test
    @DisplayName("1시간 이상 늦어도 결석")
    void attendance_absence_test3() {
        LocalDate today = LocalDate.of(2024, 12, 3);

        Attendance attendance = new Attendance();
        assertThat(attendance.attend("11:10", today)).hasFieldOrPropertyWithValue("attendanceStatus","결석");
    }

    @Test
    @DisplayName("월요일은 13시부터 시작한다. - 출석 테스트")
    void attendance_monday_test1() {
        LocalDate monday = LocalDate.of(2024, 12, 2);

        Attendance attendance = new Attendance();
        assertThat(attendance.attend("13:00", monday)).hasFieldOrPropertyWithValue("attendanceStatus","출석");
    }

    @Test
    @DisplayName("월요일은 13시부터 시작한다. - 지각 테스트")
    void attendance_monday_test2() {
        LocalDate monday = LocalDate.of(2024, 12, 2);
        Attendance attendance = new Attendance();
        assertThat(attendance.attend("13:06", monday)).hasFieldOrPropertyWithValue("attendanceStatus","지각");
    }

    @Test
    @DisplayName("월요일은 13시부터 시작한다. - 결석 테스트")
    void attendance_monday_test3() {
        LocalDate monday = LocalDate.of(2024, 12, 2);
        Attendance attendance = new Attendance();
        assertThat(attendance.attend("13:36", monday)).hasFieldOrPropertyWithValue("attendanceStatus","결석");
    }

    @Test
    @DisplayName("출석 시간이 8시 이전이면 예외")
    void attendance_not_open_test1() {
        LocalDate today = LocalDate.of(2024, 12, 2);
        Attendance attendance = new Attendance();
        assertThatThrownBy(() -> attendance.attend("7:36", today))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("캠퍼스 운영 시간은 8:00 ~ 23:00입니다.");
    }

    @Test
    @DisplayName("출석 시간이 11시 이후면 예외")
    void attendance_not_open_test2() {
        LocalDate today = LocalDate.of(2024, 12, 2);
        Attendance attendance = new Attendance();
        assertThatThrownBy(() -> attendance.attend("23:36", today))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("캠퍼스 운영 시간은 8:00 ~ 23:00입니다.");
    }

    @Test
    @DisplayName("출석일이 주말이면 예외")
    void attendance_not_open_test3() {
        LocalDate today = LocalDate.of(2024, 12, 1);
        Attendance attendance = new Attendance();
        assertThatThrownBy(() -> attendance.attend("10:00", today))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등교일이 아닙니다.");
    }

    @Test
    @DisplayName("출석일이 크리스마스면 예외")
    void attendance_not_open_test4() {
        LocalDate today = LocalDate.of(2024, 12, 25);
        Attendance attendance = new Attendance();
        assertThatThrownBy(() -> attendance.attend("10:00", today))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등교일이 아닙니다.");
    }

    @Test
    @DisplayName("출석하면 Attendance를 리턴 - 출석")
    void attendance_test1() {
        LocalDate today = LocalDate.of(2024, 12, 2);
        Attendance attendance = new Attendance();
        Attendance attend = attendance.attend("13:00", today);
        assertThat(attend).hasFieldOrPropertyWithValue("attendanceDateTime", LocalDateTime.of(2024,12,2,13,0));
        assertThat(attend).hasFieldOrPropertyWithValue("attendanceStatus", "출석");
    }

    @Test
    @DisplayName("출석하면 Attendance를 리턴 - 지각")
    void attendance_test2() {
        LocalDate today = LocalDate.of(2024, 12, 2);
        Attendance attendance = new Attendance();
        Attendance attend = attendance.attend("13:06", today);
        assertThat(attend).hasFieldOrPropertyWithValue("attendanceDateTime", LocalDateTime.of(2024,12,2,13,6));
        assertThat(attend).hasFieldOrPropertyWithValue("attendanceStatus", "지각");
    }

    @Test
    @DisplayName("출석하면 Attendance를 리턴 - 결석")
    void attendance_test3() {
        LocalDate today = LocalDate.of(2024, 12, 2);
        Attendance attendance = new Attendance();
        Attendance attend = attendance.attend("13:36", today);
        assertThat(attend).hasFieldOrPropertyWithValue("attendanceDateTime", LocalDateTime.of(2024,12,2,13,36));
        assertThat(attend).hasFieldOrPropertyWithValue("attendanceStatus", "결석");
    }
}
