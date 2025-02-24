package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AttendanceStatusTest {

    @Test
    void 출석_여부를_판단한다() {
        // given & when
        AttendanceStatus attendanceStatus = AttendanceStatus.from(
                new DateTime(new WorkDate(2024, 12, 13), new WorkTime(10, 0))
        );

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    void 지각_여부를_판단한다() {
        // given & when
        AttendanceStatus attendanceStatus = AttendanceStatus.from(
                new DateTime(new WorkDate(2024, 12, 13), new WorkTime(10, 10))
        );

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.PERCEPTION);
    }

    @Test
    void 결석_여부를_판단한다() {
        // given & when
        AttendanceStatus attendanceStatus = AttendanceStatus.from(
                new DateTime(new WorkDate(2024, 12, 13), new WorkTime(10, 35))
        );

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ABSENCE);
    }
}
