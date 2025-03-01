package attendance;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {

    AttendanceHistory attendanceHistory;
    AttendanceTimes attendanceTimes;

    @BeforeEach
    void init() {
        attendanceTimes = AttendanceTimes.create();
        attendanceHistory = AttendanceHistory.create();
    }

    @DisplayName("닉네임과 출석 기록이 주어졌을 때, 저장할 수 있어야 한다.")
    @Test
    void given_nickname_and_attendance_then_save() {
        String nickname = "젠슨";
        AttendanceTime attendanceTime = AttendanceTime.from(LocalDateTime.of(2024, 12, 3, 10, 0));
        attendanceHistory.add(nickname, attendanceTime);
        Map<String, AttendanceTimes> attendanceHistory1 = attendanceHistory.getAttendanceHistory();
        assertThat(attendanceHistory1.get(nickname).getAttendanceTimes()).contains(attendanceTime);
    }

}
