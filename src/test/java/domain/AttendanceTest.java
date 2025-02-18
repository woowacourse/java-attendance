package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import util.AttendancesFileHandler;

public class AttendanceTest {

    Attendance attendance = new Attendance(AttendancesFileHandler.generateAttendances());

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
}
