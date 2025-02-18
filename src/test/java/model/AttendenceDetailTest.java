package model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.Attendence;
import attendance.model.AttendenceDetail;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttendenceDetailTest {

    @Test
    void test1() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 0);
        AttendenceDetail attendenceDetail = new AttendenceDetail(dateTime);
        Assertions.assertThat(attendenceDetail).isNotNull();
    }

    @Test
    void test2() {
        AttendenceDetail attendenceDetail = new AttendenceDetail(LocalDateTime.of(2024, 12, 3, 10, 7));

        assertThat(attendenceDetail.getAttendence()).isEqualTo(Attendence.지각);
    }

    @Test
    void test3() {
        AttendenceDetail attendenceDetail = new AttendenceDetail(LocalDateTime.of(2024, 12, 5, 17, 6));

        assertThat(attendenceDetail.getAttendence()).isEqualTo(Attendence.결석);
    }
    @Test
    void test4() {
        AttendenceDetail attendenceDetail = new AttendenceDetail(LocalDateTime.of(2024, 12, 3, 10, 2));

        assertThat(attendenceDetail.getAttendence()).isEqualTo(Attendence.출석);
    }
}
