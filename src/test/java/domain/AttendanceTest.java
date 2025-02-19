package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
    void 이미_출석한_경우_예외() {
        String crewName = "이든";
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 10, 9, 59, 0);

        assertThatThrownBy(() -> attendance.attend(crewName, attendanceTime))
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
    void 크루의_출석_기록_통계_확인_기능() {
        Map<AttendanceStatus, Integer> attendanceStatuses = attendance.countAttendanceStatus("빙티");

        assertThat(attendanceStatuses.get(AttendanceStatus.ATTEND)).isEqualTo(3);
        assertThat(attendanceStatuses.get(AttendanceStatus.LATE)).isEqualTo(4);
        assertThat(attendanceStatuses.get(AttendanceStatus.ABSENT) + attendanceStatuses.get(AttendanceStatus.UNATTEND)).isEqualTo(4);
    }

    @Test
    void 제적_위험자_확인_기능() {
        List<String> expelledCrew = attendance.checkExpelledCrew();
        assertThat(expelledCrew).contains("빙티");
    }
}
