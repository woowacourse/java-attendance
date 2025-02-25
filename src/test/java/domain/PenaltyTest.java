package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class PenaltyTest {

    @Test
    void 경고를_판단한다() {
        // given
        Map<AttendanceStatus, Integer> attendanceStatusCount = Map.of(
                AttendanceStatus.PERCEPTION, 4,
                AttendanceStatus.ABSENCE, 1
        );

        // when
        Penalty penalty = Penalty.from(attendanceStatusCount);

        // then
        assertThat(penalty).isEqualTo(Penalty.WARNING);
    }

    @Test
    void 면담을_판단한다() {
        // given
        Map<AttendanceStatus, Integer> attendanceStatusCount = Map.of(
                AttendanceStatus.PERCEPTION, 4,
                AttendanceStatus.ABSENCE, 2
        );

        // when
        Penalty penalty = Penalty.from(attendanceStatusCount);

        // then
        assertThat(penalty).isEqualTo(Penalty.INTERVIEW);
    }

    @Test
    void 제적을_판단한다() {
        // given
        Map<AttendanceStatus, Integer> attendanceStatusCount = Map.of(
                AttendanceStatus.PERCEPTION, 4,
                AttendanceStatus.ABSENCE, 5
        );

        // when
        Penalty penalty = Penalty.from(attendanceStatusCount);

        // then
        assertThat(penalty).isEqualTo(Penalty.WEEDING);
    }

    @Test
    void 지각을_결석으로_간주하여_계산한다() {
        // given
        Map<AttendanceStatus, Integer> attendanceStatusCount = Map.of(
                AttendanceStatus.PERCEPTION, 4,
                AttendanceStatus.ABSENCE, 1
        );

        // when
        int absenceCount = Penalty.calculateAbsenceCount(attendanceStatusCount);

        // then
        assertThat(absenceCount).isEqualTo(2);
    }
}
