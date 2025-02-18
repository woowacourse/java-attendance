package model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.AttendanceDetail;
import attendance.model.AttendanceHistory;
import java.time.LocalDateTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {

    @Test
    void test1() {
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        assertThat(attendanceHistory).isNotNull();
    }

    @Test
    void test2() {
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 2, 13, 0)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 3, 10, 7)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 4, 10, 2)));

        assertThat(attendanceHistory.getAttendanceHistory()).hasSize(3);
    }

    @Test
    void test4() {
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 2, 13, 0)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 3, 9, 58)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 4, 10, 2)));

        assertThat(attendanceHistory.getAttendanceCount()).isEqualTo(3);
    }

    @Test
    void test3() {
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 2, 13, 0)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 3, 10, 7)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 4, 10, 2)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 5, 10, 6)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 6, 10, 1)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 9, 17, 0)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 10, 10, 3)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 11, 17, 2)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 12, 17, 2)));
        attendanceHistory.addAttendanceDetail(new AttendanceDetail(LocalDateTime.of(2024, 12, 13, 10, 2)));

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(attendanceHistory.getAttendanceCount()).isEqualTo(5); //5
        softly.assertThat(attendanceHistory.getLateCount()).isEqualTo(2); //2
        softly.assertThat(attendanceHistory.getAbsenceCount()).isEqualTo(3); //
        softly.assertAll();
    }

}
