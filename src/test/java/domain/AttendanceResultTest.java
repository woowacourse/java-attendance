package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceResultTest {

    @Test
    @DisplayName("출석 테스트 - 정상 출석")
    void findAttendanceResult_Attendance() {
        // given
        LocalDateTime time = LocalDateTime.of(2024, 12, 17, 10, 0);

        // when
        AttendanceResult result = AttendanceResult.findAttendanceResult(time);

        // then
        assertThat(result).isEqualTo(AttendanceResult.ATTENDANCE);
    }

    @Test
    @DisplayName("출석 테스트 - 지각")
    void findAttendanceResult_Late() {
        // given
        LocalDateTime time = LocalDateTime.of(2024, 12, 17, 10, 6);

        // when
        AttendanceResult result = AttendanceResult.findAttendanceResult(time);

        // then
        assertThat(result).isEqualTo(AttendanceResult.LATE);
    }

    @Test
    @DisplayName("출석 테스트 - 결석")
    void findAttendanceResult_Absence() {
        // given
        LocalDateTime time = LocalDateTime.of(2024, 12, 17, 10, 35);

        // when
        AttendanceResult result = AttendanceResult.findAttendanceResult(time);

        // then
        assertThat(result).isEqualTo(AttendanceResult.ABSENCE);
    }
}
