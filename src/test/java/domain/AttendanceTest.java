package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import util.AttendancesFileHandler;

public class AttendanceTest {

    Attendance attendance = new Attendance(AttendancesFileHandler.generateAttendances(), LocalDate.of(2024, 12, 17));

    public AttendanceTest() throws IOException {
    }

    @Test
    void 크루의_출석_기능() {
        String crewName = "이든";
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 16, 9, 59, 0);

        assertDoesNotThrow(() -> attendance.attend(crewName, attendanceTime));
    }

    @Test
    void 크루의_존재_예외() {
        String crewName = "행성";

        assertThatThrownBy(() -> attendance.getAttendanceTimes(crewName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이미_출석한_경우_예외() {
        String crewName = "이든";
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 10, 9, 59, 0);

        assertThatThrownBy(() -> attendance.attend(crewName, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 수정할_출석이_없는_예외() {
        String crewName = "리버";
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 16, 9, 59, 0);

        assertThatThrownBy(() -> attendance.edit(crewName, attendanceTime.getDayOfMonth(), attendanceTime.toLocalTime()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 크루의_출석_수정_기능() {
        String crewName = "빙티";

        LocalDateTime oldAttendanceDateTime = LocalDateTime.of(2024, 12, 3, 10, 7, 0);
        int attendanceDay = oldAttendanceDateTime.toLocalDate().getDayOfMonth();
        LocalDateTime newAttendanceDateTime = LocalDateTime.of(2024, 12, 3, 9, 58, 0);
        LocalTime newAttendanceTime = newAttendanceDateTime.toLocalTime();

        attendance.edit(crewName, attendanceDay, newAttendanceTime);

        List<AttendanceTime> attendanceTimes = attendance.getAttendanceTimes(crewName);
        int count = (int) attendanceTimes.stream().map(AttendanceTime::getAttendanceDateTime)
                .filter(attendanceTime -> attendanceTime.equals(newAttendanceDateTime))
                .count();

        assertThat(count).isEqualTo(1);
    }

    @Test
    void 출석_날짜_예외() {
        String crewName = "리버";
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 16, 9, 59, 0);

        assertThatThrownBy(() -> attendance.findAttendanceTime(crewName, attendanceTime.toLocalDate()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 닉네임_존재_예외() {
        String crewName = "행성";

        assertThatThrownBy(() -> attendance.validateNickName(crewName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 지각_3회_포함_결석_계산_기능() {
        String crewName = "빙티";

        assertThat(attendance.getAbsentCount(crewName)).isEqualTo(5);
    }

    @Test
    void 결석으로_계산되지_않는_지각_계산_기능() {
        String crewName = "빙티";

        assertThat(attendance.getLateCount(crewName)).isEqualTo(1);
    }

    @Test
    void 크루의_출석_기록_통계_확인_기능() {
        Map<AttendanceStatus, Integer> attendanceStatuses = attendance.getCrewAttendanceStatus("빙티");

        assertThat(attendanceStatuses.get(AttendanceStatus.ATTEND)).isEqualTo(3);
        assertThat(attendanceStatuses.get(AttendanceStatus.LATE)).isEqualTo(4);
        assertThat(attendanceStatuses.get(AttendanceStatus.ABSENT) + attendanceStatuses.get(AttendanceStatus.UNATTEND)).isEqualTo(4);
    }

    @Test
    void 제적_위험자_확인_기능() {
        List<String> expelledCrew = attendance.checkExpelledCrew();

        assertTrue(expelledCrew.contains("빙티"));
    }
}
