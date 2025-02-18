package model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.Attendence;
import attendance.model.AttendenceDetail;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
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

    @Test
    void test5() {
        AttendenceDetail attendenceDetail = new AttendenceDetail(LocalDateTime.of(2024, 12, 10, 10, 2));
        attendenceDetail.modify(LocalTime.of(9, 58));
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(attendenceDetail.getLocalDateTime()).isEqualTo(LocalDateTime.of(2024, 12, 10, 9, 58));
        softly.assertThat(attendenceDetail.getAttendence()).isEqualTo(Attendence.출석);
        softly.assertAll();
    }
}
