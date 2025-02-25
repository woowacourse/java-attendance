package domain.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.crew.CrewStatus;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceLogsTest {

    @ParameterizedTest
    @DisplayName("크루의 전체 출결 기록 상태와 경고 상태 테스트")
    @CsvSource({
            "'17', 'PASS'",
            "'17,18', 'WARNING'",
            "'17,18,19', 'CONSULT'",
            "'17,18,19,20,21,24', 'EXPEL'"

    })
    void 크루의_전체_출결_기록_상태_테스트(String days, String expectedStatus) {
        // given
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        for (String day : days.split(",")) {
            attendanceLogs.addAttendanceLog(LocalDateTime.of(2025, 2, Integer.parseInt(day), 15, 0));
        }
        // when & then
        assertEquals(CrewStatus.valueOf(expectedStatus), attendanceLogs.calculateCrewStatus());
    }
}

