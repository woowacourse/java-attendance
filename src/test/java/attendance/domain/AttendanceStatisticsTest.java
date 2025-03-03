package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.config.FixedLocalDateProvider;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("출석 지각 결석 횟수 계산")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AttendanceStatisticsTest {
    @Test
    void 전날까지의_출석_지각_결석_횟수를_반환한다() {
        LocalDate today = LocalDate.of(2024, 12, 5);
        Attendances attendances = new Attendances();
        attendances.addAttendance(LocalDate.of(2024, 12, 2), LocalTime.of(13, 10));
        attendances.addAttendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 50));
        attendances.addAttendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 6));
        AttendanceStatistics attendanceStatistics = new DefaultAttendanceStatistics(new FixedLocalDateProvider(today), new DefaultAttendanceChecker());

        Map<AttendanceStatus, Integer> count = attendanceStatistics.getTotalStatusCount(attendances);

        assertThat(count).containsEntry(AttendanceStatus.LATENESS, 2);
        assertThat(count).containsEntry(AttendanceStatus.ABSENCE, 1);
    }

    @Test
    void 등교하지_않은_날은_결석으로_간주한다() {
        LocalDate today = LocalDate.of(2024, 12, 5);
        Attendances attendances = new Attendances();
        AttendanceStatistics attendanceStatistics = new DefaultAttendanceStatistics(new FixedLocalDateProvider(today), new DefaultAttendanceChecker());

        Map<AttendanceStatus, Integer> result = attendanceStatistics.getTotalStatusCount(attendances);

        assertThat(result).containsEntry(AttendanceStatus.ABSENCE, 3);
    }
}
