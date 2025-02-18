package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    void 출석을_확인한다() {
        assertThat(Attendance.attend("시소", LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 03, 00)))).isEqualTo(0);
    }

    @Test
    void 지각을_확인한다() {
        assertThat(Attendance.attend("시소", LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 07, 00)))).isEqualTo(1);
    }

    @Test
    void 지각을_확인한다2() {
        assertThat(Attendance.attend("시소", LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 30, 00)))).isEqualTo(1);
    }

    @Test
    void 결석을_확인한다() {
        assertThat(Attendance.attend("시소", LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 31, 00)))).isEqualTo(2);
    }

    @Test
    void 해당_날짜에_출석을_저장한다() {
        LocalDate localDate = LocalDate.of(2024, 12, 10);
        LocalTime localTime = LocalTime.of(10, 04);
        Attendance.addAttendStatus("시소", LocalDateTime.of(localDate, localTime));
        assertThat(Attendance.getAttendanceTime("시소", localDate)).isEqualTo(localTime);
    }
}
