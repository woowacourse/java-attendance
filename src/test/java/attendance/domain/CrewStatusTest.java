package attendance.domain;

import static attendance.domain.AttendanceType.*;
import static attendance.domain.CrewStatus.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewStatusTest {

    @DisplayName("결석 2회 이상일 경우, 경고를 반환한다")
    @Test
    void given_attendance_result_then_return_status() {
        Map<AttendanceType, Integer> attendanceResult = new HashMap<>();
        attendanceResult.put(LATE, 3);
        attendanceResult.put(ABSENCE, 1);
        CrewStatus crewStatus = CrewStatus.calculate(attendanceResult);
        assertThat(crewStatus).isEqualTo(WARING);
    }
}
