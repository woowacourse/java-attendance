package attendance.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StatusStatisticTest {
    private final SystemDateTime systemDateTime = new AttendanceDateTime();
    private final Attendances attendances = new Attendances(systemDateTime);

    @BeforeEach
    void setup() {
        var attendance = LocalDateTime.of(2024, 12, 10, 10, 0);
        var late = LocalDateTime.of(2024, 12, 11, 10, 10);
        var absence = LocalDateTime.of(2024, 12, 12, 10, 35);
        var attendances = new Attendances(systemDateTime);
        attendances.addAttendance(attendance);
        attendances.addAttendance(late);
        attendances.addAttendance(absence);

    }

    @Test
    @DisplayName("출석 상태 통계를 갱신한다.")
    void test_updateStatusStatistic() {
        var nickname = new Nickname("이든");
        var statusStatics = new StatusStatistics(nickname);
        statusStatics.update(attendances.attendances());

        assertAll(
            () -> assertThat(statusStatics.getCount(AttendanceStatus.ATTENDANCE)).isEqualTo(1),
            () -> assertThat(statusStatics.getCount(AttendanceStatus.LATE)).isEqualTo(1),
            () -> assertThat(statusStatics.getCount(AttendanceStatus.ABSENCE)).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("출석 상태 통계에 대한 가중치를 반환한다.")
    void test_returnWeightOfStatusStatistics() {
        var nickname = new Nickname("이든");
        var statusStatics = new StatusStatistics(nickname);
        attendances.updateStatics(statusStatics);

        assertThat(statusStatics.getWeight()).isEqualTo(1);
    }

    @Test
    @DisplayName("출석 상태 통계는 제적 상태로 비교해 정렬할 수 있다.")
    void test_compareLevelOfStatusStatistics() {
        var nickname = new Nickname("이든");
        var statusStatics = new StatusStatistics(nickname);
        attendances.updateStatics(statusStatics);

        attendances.addAttendance(LocalDateTime.of(2024, 12, 16, 15, 0));
        attendances.addAttendance(LocalDateTime.of(2024, 12, 17, 15, 0));
        var otherStatusStatics = new StatusStatistics(nickname);
        attendances.updateStatics(otherStatusStatics);

        assertThat(statusStatics.compareTo(otherStatusStatics)).isLessThan(0);
    }
}
