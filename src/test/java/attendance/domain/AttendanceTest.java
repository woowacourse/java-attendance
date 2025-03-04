package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AttendanceTest {

    @Test
    @DisplayName("등교 시간이 입력되면 출석")
    void attendanceBasicTest1() {
        assertThat(new Attendance(LocalDateTime.of(2024,12,3,9,58))).hasFieldOrPropertyWithValue("status", AttendanceStatus.ATTEND);
    }

    @Test
    @DisplayName("5분 초과는 지각이다")
    void attendanceLateTest1() {
        assertThat(new Attendance(LocalDateTime.of(2024,12,3,10,6))).hasFieldOrPropertyWithValue("status",AttendanceStatus.LATE);
    }

    @Test
    @DisplayName("30분 초과는 결석이다")
    void attendanceAbsenceTest1() {
        assertThat(new Attendance(LocalDateTime.of(2024,12,3,10,36))).hasFieldOrPropertyWithValue("status",AttendanceStatus.ABSENCE);
    }

    @Test
    @DisplayName("0시 0분이면 결석")
    void attendanceAbsenceTest2() {
        assertThat(new Attendance(LocalDateTime.of(2024,12,3,0,0))).hasFieldOrPropertyWithValue("status",AttendanceStatus.ABSENCE);
    }

    @Test
    @DisplayName("1시간 이상 늦어도 결석")
    void attendanceAbsenceTest3() {
        assertThat(new Attendance(LocalDateTime.of(2024,12,3,11,36))).hasFieldOrPropertyWithValue("status",AttendanceStatus.ABSENCE);
    }

    @Test
    @DisplayName("월요일은 13시부터 시작한다. - 출석 테스트")
    void attendanceMondayTest1() {
        LocalDate monday = LocalDate.of(2024, 12, 2);
        assertThat(new Attendance(LocalDateTime.of(2024,12,2,13,0))).hasFieldOrPropertyWithValue("status",AttendanceStatus.ATTEND);
    }

    @Test
    @DisplayName("월요일은 13시부터 시작한다. - 지각 테스트")
    void attendanceMondayTest2() {
        assertThat(new Attendance(LocalDateTime.of(2024,12,2,13,6))).hasFieldOrPropertyWithValue("status",AttendanceStatus.LATE);
    }

    @Test
    @DisplayName("월요일은 13시부터 시작한다. - 결석 테스트")
    void attendanceMondayTest3() {
        assertThat(new Attendance(LocalDateTime.of(2024,12,2,13,36))).hasFieldOrPropertyWithValue("status",AttendanceStatus.ABSENCE);
    }

    @Test
    @DisplayName("출석 시간이 8시 이전이면 예외")
    void attendanceNotOpenTest1() {
        assertThatThrownBy(() -> new Attendance(LocalDateTime.of(2024,12,2,7,0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("캠퍼스 운영 시간은 8:00 ~ 23:00입니다.");
    }

    @Test
    @DisplayName("출석 시간이 23시 이후면 예외")
    void attendanceNotOpenTest2() {
        assertThatThrownBy(() -> new Attendance(LocalDateTime.of(2024,12,2,23,30)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("캠퍼스 운영 시간은 8:00 ~ 23:00입니다.");
    }

    @Test
    @DisplayName("출석일이 주말이면 예외")
    void attendanceNotOpenTest3() {
        assertThatThrownBy(() -> new Attendance(LocalDateTime.of(2024,12,1,13,0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등교일이 아닙니다.");
    }

    @Test
    @DisplayName("출석일이 크리스마스면 예외")
    void attendanceNotOpenTest4() {
        assertThatThrownBy(() -> new Attendance(LocalDateTime.of(2024,12,25,13,0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등교일이 아닙니다.");
    }

    @Test
    @DisplayName("출석하면 Attendance를 리턴 - 출석")
    void attendanceTest1() {
        Attendance attend = new Attendance(LocalDateTime.of(2024,12,2,13,0));
        assertThat(attend).hasFieldOrPropertyWithValue("dateTime", LocalDateTime.of(2024,12,2,13,0));
        assertThat(attend).hasFieldOrPropertyWithValue("status", AttendanceStatus.ATTEND);
    }

    @Test
    @DisplayName("출석하면 Attendance를 리턴 - 지각")
    void attendanceTest2() {
        Attendance attend = new Attendance(LocalDateTime.of(2024,12,2,13,6));
        assertThat(attend).hasFieldOrPropertyWithValue("dateTime", LocalDateTime.of(2024,12,2,13,6));
        assertThat(attend).hasFieldOrPropertyWithValue("status", AttendanceStatus.LATE);
    }

    @Test
    @DisplayName("출석하면 Attendance를 리턴 - 결석")
    void attendanceTest3() {
        Attendance attend = new Attendance(LocalDateTime.of(2024,12,2,13,36));
        assertThat(attend).hasFieldOrPropertyWithValue("dateTime", LocalDateTime.of(2024,12,2,13,36));
        assertThat(attend).hasFieldOrPropertyWithValue("status", AttendanceStatus.ABSENCE);
    }
}
