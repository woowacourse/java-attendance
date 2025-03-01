package attendance.domain;

import static attendance.domain.AttendanceType.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceResultTest {

    @DisplayName("출석 기록이 주어졌을 때, 출석유형과 횟수를 판별한다.")
    @Test
    void given_attendance_times_then_calculate_attendance_result() {
        AttendanceTimes attendanceTimes = AttendanceTimes.create();
        AttendanceTime attendAttendanceTime1 = AttendanceTime.from(LocalDateTime.of(2024, 12, 2, 13, 0));
        AttendanceTime attendAttendanceTime2 = AttendanceTime.from(LocalDateTime.of(2024, 12, 3, 10, 0));
        AttendanceTime lateAttendanceTime1 = AttendanceTime.from(LocalDateTime.of(2024, 12, 4, 10, 8));
        AttendanceTime lateAttendanceTime2 = AttendanceTime.from(LocalDateTime.of(2024, 12, 5, 10, 20));
        AttendanceTime absenceAttendanceTime1 = AttendanceTime.from(LocalDateTime.of(2024, 12, 6, 10, 30));
        attendanceTimes.add(attendAttendanceTime1);
        attendanceTimes.add(attendAttendanceTime2);
        attendanceTimes.add(lateAttendanceTime1);
        attendanceTimes.add(lateAttendanceTime2);
        attendanceTimes.add(absenceAttendanceTime1);

        LocalDate currentDate = LocalDate.of(2024, 12, 7);
        Map<AttendanceType, Integer> result = AttendanceResult.calculateAttendanceResult(currentDate, attendanceTimes);
        assertThat(result.get(ATTENDANCE)).isEqualTo(2);
        assertThat(result.get(LATE)).isEqualTo(2);
        assertThat(result.get(ABSENCE)).isEqualTo(1);
    }
}
