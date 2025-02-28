package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceStatisticsTest {
    @DisplayName("출석_지각_결석_횟수를_기반으로_크루_상태를_반환할_수_있다")
    @CsvSource(value = {"1:3:0:NONE", "1:3:1:WARNING", "4:0:3:INTERVIEW", "2:10:3:FIRE"}, delimiterString = ":")
    @ParameterizedTest
    void calculateCrewStatus(int attendanceCount, int lateCount, int absenceCount, CrewStatus expected) {
        //given
        AttendanceStatistics attendanceStatistics = new AttendanceStatistics(attendanceCount, lateCount, absenceCount);

        //when
        CrewStatus result = attendanceStatistics.calculateCrewStatus();

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("주어진_출석_상태의_횟수를_반환할_수_있다")
    @CsvSource(value = {"ATTENDANCE:1", "LATE:2", "ABSENCE:3"}, delimiterString = ":")
    @ParameterizedTest
    void getStatusCount(AttendanceStatus status, int expected) {
        //given
        AttendanceStatistics attendanceStatistics = new AttendanceStatistics(1, 2, 3);

        //when
        int result = attendanceStatistics.getStatusCount(status);

        //then
        assertThat(result).isEqualTo(expected);
    }
}
