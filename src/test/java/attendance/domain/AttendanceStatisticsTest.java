package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceStatisticsTest {
    @DisplayName("d")
    @CsvSource(value = {"1:3:0:NONE", "1:3:1:WARNING", "4:0:3:INTERVIEW", "2:7:3:FIRE"}, delimiterString = ":")
    @ParameterizedTest
    void should_ReturnDangerousStatus(int attendanceCount, int lateCount, int absenceCount, String dangerousStatus) {
        //given
        AttendanceStatistics attendanceStatistics = new AttendanceStatistics(attendanceCount, lateCount, absenceCount);

        //when
        String result = attendanceStatistics.calculateDangerousStatus();

        //then
        assertThat(result).isEqualTo(dangerousStatus);
    }
}
