package model;

import attendance.model.Attendence;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttendenceTest {

    @Test
    void test7() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 0);

        Attendence attendence = Attendence.from(dateTime);
        Assertions.assertThat(attendence).isEqualTo(Attendence.출석);
    }

    @Test
    void test8() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 6);

        Attendence attendence = Attendence.from(dateTime);
        Assertions.assertThat(attendence).isEqualTo(Attendence.지각);
    }

    @Test
    void test9() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 31);

        Attendence attendence = Attendence.from(dateTime);
        Assertions.assertThat(attendence).isEqualTo(Attendence.결석);
    }

}
