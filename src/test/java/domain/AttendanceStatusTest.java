package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;
import java.util.Map;
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

    @Test
    void 출석_상태_개수를_계산한다() {
        // given
        WorkDateTime workDateTime1 = new WorkDateTime(new WorkDate(2024, 12, 13), new WorkTime(10, 0));
        WorkDateTime workDateTime2 = new WorkDateTime(new WorkDate(2024, 12, 13), new WorkTime(10, 6));
        WorkDateTime workDateTime3 = new WorkDateTime(new WorkDate(2024, 12, 13), new WorkTime(10, 35));
        WorkDateTime workDateTime4 = new WorkDateTime(new WorkDate(2024, 12, 13), new WorkTime(null, null));
        List<WorkDateTime> workDateTimes = List.of(workDateTime1, workDateTime2, workDateTime3, workDateTime4);

        // when
        Map<AttendanceStatus, Integer> statusCount = AttendanceStatus.calculateAttendanceStatusCount(
                workDateTimes);

        // then
        assertSoftly(softly -> {
            softly.assertThat(statusCount.get(AttendanceStatus.ATTENDANCE)).isEqualTo(1);
            softly.assertThat(statusCount.get(AttendanceStatus.PERCEPTION)).isEqualTo(1);
            softly.assertThat(statusCount.get(AttendanceStatus.ABSENCE)).isEqualTo(2);
        });
    }
}
