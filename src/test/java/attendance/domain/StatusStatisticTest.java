package attendance.domain;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StatusStatisticTest {
    private final SystemDateTime systemDateTime = new AttendanceDateTime();
    private final Attendances attendances = new Attendances(systemDateTime);

    @Test
    @DisplayName("출석 상태 통계에 대한 가중치를 반환한다.")
    void test_returnWeightOfStatusStatistics() {
        var attendance = LocalDateTime.of(2024, 12, 10, 10, 0);
        var late = LocalDateTime.of(2024, 12, 11, 10, 10);
        var absence = LocalDateTime.of(2024, 12, 12, 10, 35);
        attendances.addAttendance(attendance);
        attendances.addAttendance(late);
        attendances.addAttendance(absence);
        StatusStatistics statistics = attendances.calculateStatics();

        Assertions.assertThat(statistics.getWeight()).isEqualTo(1);
    }
}
