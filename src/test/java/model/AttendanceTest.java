package model;

import attendance.model.Attendance;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    void test7() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 0);

        Attendance attendance = Attendance.from(dateTime);
        Assertions.assertThat(attendance).isEqualTo(Attendance.출석);
    }

    @Test
    void test8() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 6);

        Attendance attendance = Attendance.from(dateTime);
        Assertions.assertThat(attendance).isEqualTo(Attendance.지각);
    }

    @Test
    void test9() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 31);

        Attendance attendance = Attendance.from(dateTime);
        Assertions.assertThat(attendance).isEqualTo(Attendance.결석);
    }

}
