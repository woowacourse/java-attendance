import static org.assertj.core.api.Assertions.assertThat;

import domain.Attendance;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    @DisplayName("닉네임과 시간을 통해 기록 - 출석")
    void attendanceTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 10, 0);
        Attendance attendance = new Attendance();
        String name = "벡터";
        String attendanceStatus = attendance.checkAttendance(name, attendanceTime);
        assertThat(attendanceStatus).isEqualTo("출석");
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록 - 지각")
    void lateTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 10, 6);
        Attendance attendance = new Attendance();
        String name = "벡터";
        String attendanceStatus = attendance.checkAttendance(name, attendanceTime);
        assertThat(attendanceStatus).isEqualTo("지각");
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록 - 결석")
    void absentTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 10, 35);
        Attendance attendance = new Attendance();
        String name = "벡터";
        String attendanceStatus = attendance.checkAttendance(name, attendanceTime);
        assertThat(attendanceStatus).isEqualTo("결석");
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록(월요일) - 출석")
    void mondayAttendanceTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 2, 13, 0);
        Attendance attendance = new Attendance();
        String name = "벡터";
        String attendanceStatus = attendance.checkAttendance(name, attendanceTime);
        assertThat(attendanceStatus).isEqualTo("출석");
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록(월요일) - 지각")
    void mondayLateTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 2, 13, 10);
        Attendance attendance = new Attendance();
        String name = "벡터";
        String attendanceStatus = attendance.checkAttendance(name, attendanceTime);
        assertThat(attendanceStatus).isEqualTo("지각");
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록(월요일) - 결석")
    void mondayAbsentTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 2, 13, 35);
        Attendance attendance = new Attendance();
        String name = "벡터";
        String attendanceStatus = attendance.checkAttendance(name, attendanceTime);
        assertThat(attendanceStatus).isEqualTo("결석");
    }
}
