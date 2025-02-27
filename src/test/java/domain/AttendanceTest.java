package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

class AttendanceTest {

    @Test
    void 출석시간을_통해_출석체크한다() {
        LocalTime attendanceTime = LocalTime.of(10, 0);
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), attendanceTime);

        assertThatCode(() -> new Attendance(localDateTime))
                .doesNotThrowAnyException();
    }

    @Test
    void 토요일_출석은_예외를_발생시킨다() {
        LocalTime attendanceTime = LocalTime.of(10, 0);
        LocalDate saturday = LocalDate.of(2025, 3, 1);
        LocalDateTime localDateTime = LocalDateTime.of(saturday, attendanceTime);

        assertThatThrownBy(() -> new Attendance(localDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR] 주말은 등교일이 아닙니다.");
    }

    @Test
    void 일요일_출석은_예외를_발생시킨다() {
        LocalTime attendanceTime = LocalTime.of(10, 0);
        LocalDate sunday = LocalDate.of(2025, 3, 2);
        LocalDateTime localDateTime = LocalDateTime.of(sunday, attendanceTime);

        assertThatThrownBy(() -> new Attendance(localDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR] 주말은 등교일이 아닙니다.");
    }

    @Test
    void 수정일자에_따라_출석을_수정한다() {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 27, 10, 0));

        LocalDateTime localDateTime = attendance.updateAttendance(LocalTime.of(10, 6));
        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();

        assertThat(localDate).isEqualTo(LocalDate.of(2025, 2, 27));
        assertThat(localTime).isEqualTo(LocalTime.of(10, 6));
    }

    @Test
    void 월요일_출석_테스트() {
        Attendance attendance = new Attendance(
                LocalDateTime.of(2024, 12, 16, 13, 0)
        );

        AttendanceStatus attendanceStatus = attendance.judge();

        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("출석");
    }

    @Test
    void 월요일_출석_경계값_테스트() {
        Attendance attendance = new Attendance(
                LocalDateTime.of(2024, 12, 16, 13, 5)
        );

        AttendanceStatus attendanceStatus = attendance.judge();

        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("출석");
    }

    @Test
    void 월요일_지각_테스트() {
        Attendance attendance = new Attendance(
                LocalDateTime.of(2024, 12, 16, 13, 6)
        );

        AttendanceStatus attendanceStatus = attendance.judge();

        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("지각");
    }

    @Test
    void 월요일_지각_경계값_테스트() {
        Attendance attendance = new Attendance(
                LocalDateTime.of(2024, 12, 16, 13, 30)
        );

        AttendanceStatus attendanceStatus = attendance.judge();

        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("지각");
    }

    @Test
    void 월요일_결석_테스트() {
        Attendance attendance = new Attendance(
                LocalDateTime.of(2024, 12, 16, 13, 31)
        );

        AttendanceStatus attendanceStatus = attendance.judge();

        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("결석");
    }

    @Test
    void 화요일_출석_테스트() {
        Attendance attendance = new Attendance(
                LocalDateTime.of(2024, 12, 17, 10, 0)
        );

        AttendanceStatus attendanceStatus = attendance.judge();

        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("출석");
    }

    @Test
    void 화요일_출석_경계값_테스트() {
        Attendance attendance = new Attendance(
                LocalDateTime.of(2024, 12, 17, 10, 5)
        );

        AttendanceStatus attendanceStatus = attendance.judge();

        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("출석");
    }

    @Test
    void 화요일_지각_테스트() {
        Attendance attendance = new Attendance(
                LocalDateTime.of(2024, 12, 17, 10, 6)
        );

        AttendanceStatus attendanceStatus = attendance.judge();

        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("지각");
    }

    @Test
    void 화요일_지각_경계값_테스트() {
        Attendance attendance = new Attendance(
                LocalDateTime.of(2024, 12, 17, 10, 30)
        );

        AttendanceStatus attendanceStatus = attendance.judge();

        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("지각");
    }

    @Test
    void 화요일_결석_테스트() {
        Attendance attendance = new Attendance(
                LocalDateTime.of(2024, 12, 17, 10, 31)
        );

        AttendanceStatus attendanceStatus = attendance.judge();

        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("결석");
    }

    @Test
    void 주말의_경우_예외를_발생시킨다() {
        assertThatThrownBy(() ->
                new Attendance(LocalDateTime.of(2024, 12, 28, 10, 0)));
    }
}
