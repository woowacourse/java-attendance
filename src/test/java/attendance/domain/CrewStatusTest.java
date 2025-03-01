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
    void given_over_two_absence_then_return_warning() {
        Map<AttendanceType, Integer> attendanceResult = new HashMap<>();
        attendanceResult.put(LATE, 3);
        attendanceResult.put(ABSENCE, 1);
        CrewStatus crewStatus = CrewStatus.calculate(AttendanceResult.from(attendanceResult));
        assertThat(crewStatus).isEqualTo(WARING);
    }

    @DisplayName("결석 3회 이상일 경우, 면담을 반환한다")
    @Test
    void given_over_three_absence_then_return_interview() {
        Map<AttendanceType, Integer> attendanceResult = new HashMap<>();
        attendanceResult.put(LATE, 3);
        attendanceResult.put(ABSENCE, 2);
        CrewStatus crewStatus = CrewStatus.calculate(AttendanceResult.from(attendanceResult));
        assertThat(crewStatus).isEqualTo(INTERVIEW);
    }

    @DisplayName("결석 5회 이상일 경우, 제적을 반환한다")
    @Test
    void given_over_fifth_absence_then_return_absence() {
        Map<AttendanceType, Integer> attendanceResult = new HashMap<>();
        attendanceResult.put(LATE, 12);
        attendanceResult.put(ABSENCE, 1);
        CrewStatus crewStatus = CrewStatus.calculate(AttendanceResult.from(attendanceResult));
        assertThat(crewStatus).isEqualTo(FIRE);
    }
}
