package model;

import attendance.model.Attendance;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    @Test
    void 정시에_도착한_경우_출석이다() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 0);
        Attendance attendance = Attendance.from(dateTime);
        Assertions.assertThat(attendance).isEqualTo(Attendance.출석);
    }

    @Test
    void _5분_초과로_늦게온_경우_지각이다() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 6);
        Attendance attendance = Attendance.from(dateTime);
        Assertions.assertThat(attendance).isEqualTo(Attendance.지각);
    }

    @Test
    void _31분_초과로_늦게온_경우_결석이다() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 31);
        Attendance attendance = Attendance.from(dateTime);
        Assertions.assertThat(attendance).isEqualTo(Attendance.결석);
    }
}
