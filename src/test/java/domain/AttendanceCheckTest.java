package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AttendanceCheckTest {

    private final AttendanceCheck attendanceCheck = new AttendanceCheck();
    private LocalDate date;

    @Test
    void 월요일_출석_테스트() {
        date = LocalDate.of(2024, 12, 16);

        LocalTime attendanceTime = LocalTime.of(13, 0);
        AttendanceStatus attendanceStatus = attendanceCheck.judge(attendanceTime, date);
        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("출석");
    }

    @Test
    void 월요일_출석_경계값_테스트() {
        date = LocalDate.of(2024, 12, 16);

        LocalTime attendanceTime = LocalTime.of(13, 5);
        AttendanceStatus attendanceStatus = attendanceCheck.judge(attendanceTime, date);
        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("출석");
    }

    @Test
    void 월요일_지각_테스트() {
        date = LocalDate.of(2024, 12, 16);

        LocalTime attendanceTime = LocalTime.of(13, 30);
        AttendanceStatus attendanceStatus = attendanceCheck.judge(attendanceTime, date);
        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("지각");
    }

    @Test
    void 월요일_지각_경계값_테스트() {
        date = LocalDate.of(2024, 12, 16);

        LocalTime attendanceTime = LocalTime.of(13, 30);
        AttendanceStatus attendanceStatus = attendanceCheck.judge(attendanceTime, date);
        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("지각");
    }

    @Test
    void 월요일_결석_테스트() {
        date = LocalDate.of(2024, 12, 16);

        LocalTime attendanceTime = LocalTime.of(13, 31);
        AttendanceStatus attendanceStatus = attendanceCheck.judge(attendanceTime, date);
        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("결석");
    }

    @Test
    void 화요일_출석_테스트() {
        date = LocalDate.of(2024, 12, 17);

        LocalTime attendanceTime = LocalTime.of(10, 0);
        AttendanceStatus attendanceStatus = attendanceCheck.judge(attendanceTime, date);
        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("출석");
    }

    @Test
    void 화요일_출석_경계값_테스트() {
        date = LocalDate.of(2024, 12, 17);

        LocalTime attendanceTime = LocalTime.of(10, 5);
        AttendanceStatus attendanceStatus = attendanceCheck.judge(attendanceTime, date);
        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("출석");
    }

    @Test
    void 화요일_지각_테스트() {
        date = LocalDate.of(2024, 12, 17);

        LocalTime attendanceTime = LocalTime.of(10, 30);
        AttendanceStatus attendanceStatus = attendanceCheck.judge(attendanceTime, date);
        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("지각");
    }

    @Test
    void 화요일_지각_경계값_테스트() {
        date = LocalDate.of(2024, 12, 17);

        LocalTime attendanceTime = LocalTime.of(10, 30);
        AttendanceStatus attendanceStatus = attendanceCheck.judge(attendanceTime, date);
        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("지각");
    }

    @Test
    void 화요일_결석_테스트() {
        date = LocalDate.of(2024, 12, 17);

        LocalTime attendanceTime = LocalTime.of(10, 31);
        AttendanceStatus attendanceStatus = attendanceCheck.judge(attendanceTime, date);
        Assertions.assertThat(attendanceStatus.getName()).isEqualTo("결석");
    }

    @Test
    void 주말의_경우_예외를_발생시킨다() {
        date = LocalDate.of(2024, 12, 28);
        LocalTime attendanceTime = LocalTime.of(10, 0);

        assertThatThrownBy(() -> attendanceCheck.judge(attendanceTime, date));
    }
}
