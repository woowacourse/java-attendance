package attendance.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AttendanceStatusTest {

    @Test
    void 월요일_지각상태를_찾는다() {
        // given
        LocalDate monday = LocalDate.of(2024, 12, 2);
        LocalTime lateTime = LocalTime.of(13, 6);

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.findAttendanceStatus(monday, lateTime);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    void 금요일_결석상태를_찾는다() {
        // given
        LocalDate friday = LocalDate.of(2024, 12, 6);
        LocalTime absenceTime = LocalTime.of(10, 31);

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.findAttendanceStatus(friday, absenceTime);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    void 화요일_출석상태를_찾는다() {
        // given
        LocalDate tuesday = LocalDate.of(2024, 12, 3);
        LocalTime presenceTime = LocalTime.of(10, 2);

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.findAttendanceStatus(tuesday, presenceTime);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.PRESENCE);
    }

    @Test
    void 초기맵을_생성한다() {
        // when
        Map<AttendanceStatus, Integer> attendanceStatusCounts = AttendanceStatus.initMap();

        // then
        assertThat(attendanceStatusCounts).isEqualTo(Map.of(
            AttendanceStatus.LATE, 0,
            AttendanceStatus.ABSENCE, 0,
            AttendanceStatus.PRESENCE, 0));
    }
}
