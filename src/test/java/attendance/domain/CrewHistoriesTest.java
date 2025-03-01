package attendance.domain;

import static attendance.fixture.TestFixture.makeAttendanceTime;
import static attendance.fixture.TestFixture.makeCrewHistory;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CrewHistoriesTest {

    private CrewHistories crewHistories;

    @BeforeEach
    void setUp() {
        crewHistories = new CrewHistories(new HashMap<>());
    }

    @Test
    void 크루의_출석_기록을_저장한다() {
        // Given
        Nickname nickname = new Nickname("밍트");
        LocalDateTime attendanceTime = makeAttendanceTime();

        // When
        crewHistories.addHistory(nickname, attendanceTime);

        // Then
        assertThat(crewHistories).isEqualTo(new CrewHistories(Map.of(nickname, makeCrewHistory(attendanceTime))));
    }
}
