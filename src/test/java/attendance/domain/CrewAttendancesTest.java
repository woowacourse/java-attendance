package attendance.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class CrewAttendancesTest {

    @Test
    void 전체_크루의_모든_출석_기록을_저장한다() {
        Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes = Map.of(new Crew("빙봉"), List.of(
                LocalDateTime.of(2025, 2, 26, 10, 0)
        ));

        assertDoesNotThrow(() -> new CrewAttendances(crewAttendanceDateTimes));
    }

}
