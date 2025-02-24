package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.Test;

class AttendanceStatusTest {
    
    @Test
    void 출석_여부를_판단한다() {
        // given & when
        AttendanceStatus attendanceStatus = AttendanceStatus.from(
                new WorkDateTime(new WorkDate(2024, 12, 13), new WorkTime(10, 0))
        );

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    void 지각_여부를_판단한다() {
        // given & when
        AttendanceStatus attendanceStatus = AttendanceStatus.from(
                new WorkDateTime(new WorkDate(2024, 12, 13), new WorkTime(10, 10))
        );

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.PERCEPTION);
    }

    @Test
    void 결석_여부를_판단한다() {
        // given & when
        AttendanceStatus attendanceStatus1 = AttendanceStatus.from(
                new WorkDateTime(new WorkDate(2024, 12, 13), new WorkTime(10, 35))
        );
        AttendanceStatus attendanceStatus2 = AttendanceStatus.from(
                new WorkDateTime(new WorkDate(2024, 12, 13), new WorkTime(null, null))
        );

        // then
        assertSoftly(softly -> {
            softly.assertThat(attendanceStatus1).isEqualTo(AttendanceStatus.ABSENCE);
            softly.assertThat(attendanceStatus2).isEqualTo(AttendanceStatus.ABSENCE);
        });
    }
}
