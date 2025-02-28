package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class AttendanceStatusTest {

    @Test
    void 월요일_출석_테스트() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 16, 13, 0);

        AttendanceStatus attendanceStatus = AttendanceStatus.judge(localDateTime);

        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("출석");
    }

    @Test
    void 월요일_출석_경계값_테스트() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 16, 13, 5);

        AttendanceStatus attendanceStatus = AttendanceStatus.judge(localDateTime);

        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("출석");
    }

    @Test
    void 월요일_지각_테스트() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 16, 13, 6);

        AttendanceStatus attendanceStatus = AttendanceStatus.judge(localDateTime);

        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("지각");
    }

    @Test
    void 월요일_지각_경계값_테스트() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 16, 13, 30);

        AttendanceStatus attendanceStatus = AttendanceStatus.judge(localDateTime);

        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("지각");
    }

    @Test
    void 월요일_결석_테스트() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 16, 13, 31);

        AttendanceStatus attendanceStatus = AttendanceStatus.judge(localDateTime);

        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("결석");
    }

    @Test
    void 화요일_출석_테스트() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 17, 10, 0);

        AttendanceStatus attendanceStatus = AttendanceStatus.judge(localDateTime);

        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("출석");
    }

    @Test
    void 화요일_출석_경계값_테스트() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 17, 10, 5);

        AttendanceStatus attendanceStatus = AttendanceStatus.judge(localDateTime);

        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("출석");
    }

    @Test
    void 화요일_지각_테스트() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 17, 10, 6);

        AttendanceStatus attendanceStatus = AttendanceStatus.judge(localDateTime);

        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("지각");
    }

    @Test
    void 화요일_지각_경계값_테스트() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 17, 10, 30);

        AttendanceStatus attendanceStatus = AttendanceStatus.judge(localDateTime);

        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("지각");
    }

    @Test
    void 화요일_결석_테스트() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 17, 10, 31);

        AttendanceStatus attendanceStatus = AttendanceStatus.judge(localDateTime);

        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("결석");
    }
}
