package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("출석 상태 통계 테스트")
public class StatusStatisticsTest {

    @ParameterizedTest
    @CsvSource(value = {"ATTEND:1", "LATE:1", "ABSENT:7"}, delimiterString = ":")
    void 어제까지의_출석리스트를_받아_출석_통계를_반환한다(AttendanceStatus status, int expectedCount) {
        Attendance attendance = Attendance.of(LocalDate.of(2024, 12, 11), EducationTime.GENERAL_ATTEND.getTime());
        Attendance attendanceOfLate = Attendance.of(LocalDate.of(2024, 12, 12), EducationTime.GENERAL_LATE.getTime().plusNanos(1));
        LocalDate today = LocalDate.of(2024, 12, 13);
        Attendances attendances = new Attendances(List.of(attendance, attendanceOfLate));
        StatusStatistics statusStatistics = new StatusStatistics(attendances, today);

        assertThat(statusStatistics.getAttendanceStatusCount(status)).isEqualTo(expectedCount);
    }
}
