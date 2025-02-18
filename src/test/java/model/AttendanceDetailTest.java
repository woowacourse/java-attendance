package model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.Attendance;
import attendance.model.AttendanceDetail;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class AttendanceDetailTest {

    @Test
    void test1() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 10, 10, 0);
        AttendanceDetail attendanceDetail = new AttendanceDetail(dateTime);
        Assertions.assertThat(attendanceDetail).isNotNull();
    }

    @Test
    void test2() {
        AttendanceDetail attendanceDetail = new AttendanceDetail(LocalDateTime.of(2024, 12, 3, 10, 7));

        assertThat(attendanceDetail.getAttandence()).isEqualTo(Attendance.지각);
    }

    @Test
    void test3() {
        AttendanceDetail attendanceDetail = new AttendanceDetail(LocalDateTime.of(2024, 12, 5, 17, 6));

        assertThat(attendanceDetail.getAttandence()).isEqualTo(Attendance.결석);
    }

    @Test
    void test4() {
        AttendanceDetail attendanceDetail = new AttendanceDetail(LocalDateTime.of(2024, 12, 3, 10, 2));

        assertThat(attendanceDetail.getAttandence()).isEqualTo(Attendance.출석);
    }

    @Test
    void test5() {
        AttendanceDetail attendanceDetail = new AttendanceDetail(LocalDateTime.of(2024, 12, 10, 10, 2));
        attendanceDetail.modify(LocalTime.of(9, 58));
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(attendanceDetail.getLocalDateTime()).isEqualTo(LocalDateTime.of(2024, 12, 10, 9, 58));
        softly.assertThat(attendanceDetail.getAttandence()).isEqualTo(Attendance.출석);
        softly.assertAll();
    }
}
