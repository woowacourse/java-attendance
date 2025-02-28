package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceCountsTest {

    @ParameterizedTest
    @CsvSource(value = {
            "1,3,5,EXPULSION",
            "2,3,4,COUNSELING",
            "5,1,3,COUNSELING",
            "2,0,2,WARNING",
            "3,0,1,NORMAL",
            "1,1,1,NORMAL"
    })
    void 출결_횟수_정보로_출결_상태를_계산한다(int attendance, int tardiness, int absence, AttendanceRiskLevel riskLevel) {
        // when
        AttendanceCounts attendanceCounts = AttendanceCounts.ofStatusCounts(attendance, tardiness, absence);
        AttendanceRiskLevel attendanceRiskLevel = attendanceCounts.calculateAttendanceRiskLevel();

        // then
        assertThat(attendanceRiskLevel).isEqualTo(riskLevel);
    }

}
