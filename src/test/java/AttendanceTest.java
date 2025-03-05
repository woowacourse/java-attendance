import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Attendance;
import domain.Crew;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    @DisplayName("닉네임과 시간을 통해 기록 - 출석")
    void attendanceTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 10, 0);
        Attendance attendance = new Attendance();
        Crew crew = new Crew("벡터");
        String attendanceStatus = attendance.checkAttendance(crew, attendanceTime);
        assertThat(attendanceStatus).isEqualTo("출석");
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록 - 지각")
    void lateTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 10, 6);
        Attendance attendance = new Attendance();
        Crew crew = new Crew("벡터");
        String attendanceStatus = attendance.checkAttendance(crew, attendanceTime);
        assertThat(attendanceStatus).isEqualTo("지각");
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록 - 결석")
    void absentTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 10, 35);
        Attendance attendance = new Attendance();
        Crew crew = new Crew("벡터");
        String attendanceStatus = attendance.checkAttendance(crew, attendanceTime);
        assertThat(attendanceStatus).isEqualTo("결석");
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록(월요일) - 출석")
    void mondayAttendanceTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 2, 13, 0);
        Attendance attendance = new Attendance();
        Crew crew = new Crew("벡터");
        String attendanceStatus = attendance.checkAttendance(crew, attendanceTime);
        assertThat(attendanceStatus).isEqualTo("출석");
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록(월요일) - 지각")
    void mondayLateTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 2, 13, 10);
        Attendance attendance = new Attendance();
        Crew crew = new Crew("벡터");
        String attendanceStatus = attendance.checkAttendance(crew, attendanceTime);
        assertThat(attendanceStatus).isEqualTo("지각");
    }

    @Test
    @DisplayName("닉네임과 시간을 통해 기록(월요일) - 결석")
    void mondayAbsentTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 2, 13, 35);
        Attendance attendance = new Attendance();
        Crew crew = new Crew("벡터");
        String attendanceStatus = attendance.checkAttendance(crew, attendanceTime);
        assertThat(attendanceStatus).isEqualTo("결석");
    }

    @Test
    @DisplayName("주말 출석 예외 처리")
    void testSundayException() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 7, 10, 0);
        Attendance attendance = new Attendance();
        Crew crew = new Crew("벡터");
        assertThatThrownBy(() -> attendance.checkAttendance(crew, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("크리스마스 예외처리")
    void validateHolidayException() {
        Attendance attendance = new Attendance();
        Crew crew = new Crew("벡터`");
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 25, 10, 0);
        assertThatThrownBy(() -> attendance.checkAttendance(crew, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
