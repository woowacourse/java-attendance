package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class PenaltyTest {

    @Test
    void 경고를_판단한다() {
        // given
        List<AttendanceStatus> attendanceStatuses = List.of(
                AttendanceStatus.PERCEPTION,
                AttendanceStatus.PERCEPTION,
                AttendanceStatus.PERCEPTION,
                AttendanceStatus.PERCEPTION,
                AttendanceStatus.ABSENCE
        );

        // when
        Penalty penalty = Penalty.from(attendanceStatuses);

        // then
        assertThat(penalty).isEqualTo(Penalty.WARNING);
    }

    @Test
    void 면담을_판단한다() {
        // given
        List<AttendanceStatus> attendanceStatuses = List.of(
                AttendanceStatus.PERCEPTION,
                AttendanceStatus.PERCEPTION,
                AttendanceStatus.PERCEPTION,
                AttendanceStatus.PERCEPTION,
                AttendanceStatus.ABSENCE,
                AttendanceStatus.ABSENCE
        );

        // when
        Penalty penalty = Penalty.from(attendanceStatuses);

        // then
        assertThat(penalty).isEqualTo(Penalty.INTERVIEW);
    }

    @Test
    void 제적을_판단한다() {
        // given
        List<AttendanceStatus> attendanceStatuses = List.of(
                AttendanceStatus.PERCEPTION,
                AttendanceStatus.PERCEPTION,
                AttendanceStatus.PERCEPTION,
                AttendanceStatus.PERCEPTION,
                AttendanceStatus.ABSENCE,
                AttendanceStatus.ABSENCE,
                AttendanceStatus.ABSENCE,
                AttendanceStatus.ABSENCE,
                AttendanceStatus.ABSENCE
        );

        // when
        Penalty penalty = Penalty.from(attendanceStatuses);

        // then
        assertThat(penalty).isEqualTo(Penalty.WEEDING);
    }

}
