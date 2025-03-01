package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.fixture.AttendancesTestFixture;
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
        int today = 28;
        Attendances attendances = AttendancesTestFixture.createAttendances(5, 3, today);

        Map<AttendanceStatus, Integer> count = AttendanceStatistics.getTotalStatusCount(attendances, today);

        assertThat(count).containsEntry(AttendanceStatus.LATENESS, 5);
        assertThat(count).containsEntry(AttendanceStatus.ABSENCE, 3);
    }

    @Test
    void 등교하지_않은_날은_결석으로_간주한다() {
        int today = 6;
        Attendances attendances = new Attendances();

        Map<AttendanceStatus, Integer> result =  AttendanceStatistics.getTotalStatusCount(attendances, today);

        assertThat(result).containsEntry(AttendanceStatus.ABSENCE, 2);
    }
}
