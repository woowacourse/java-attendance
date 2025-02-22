package model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.Attendance;
import attendance.model.AttendanceDetail;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class AttendanceDetailTest {

    @Test
    void 같은_출석상태일때_true를_반환한다() {
        AttendanceDetail attendanceDetail = new AttendanceDetail(LocalDateTime.of(2024, 12, 10, 10, 2));
        assertThat(attendanceDetail.isSameAs(Attendance.PRESENT)).isTrue();
    }

    @Test
    void 다른_출석상태일때_false를_반환한다() {
        AttendanceDetail attendanceDetail = new AttendanceDetail(LocalDateTime.of(2024, 12, 10, 10, 2));
        assertThat(attendanceDetail.isSameAs(Attendance.LATE)).isFalse();
    }

    @Test
    void 등교시간_수정후_결과가_반영된다() {
        AttendanceDetail attendanceDetail = new AttendanceDetail(LocalDateTime.of(2024, 12, 10, 10, 2));
        attendanceDetail.modify(LocalTime.of(9, 58));
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(attendanceDetail.getAttendanceDateTime()).isEqualTo(LocalDateTime.of(2024, 12, 10, 9, 58));
        softly.assertThat(attendanceDetail.getAttendance()).isEqualTo(Attendance.PRESENT);
        softly.assertAll();
    }

}
