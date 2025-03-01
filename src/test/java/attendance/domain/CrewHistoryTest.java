package attendance.domain;

import static attendance.fixture.TestFixture.makeAttendanceTime;
import static attendance.fixture.TestFixture.makeCrewHistory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CrewHistoryTest {

    private CrewHistory crewHistory;

    @BeforeEach
    void setUp() {
        crewHistory = new CrewHistory(new HashMap<>());
    }

    @Test
    void 출석_기록을_저장한다() {
        // Given
        LocalDateTime attendanceTime = makeAttendanceTime();

        // When
        crewHistory.add(attendanceTime);

        // Then
        assertThat(crewHistory).isEqualTo(makeCrewHistory(attendanceTime));
    }

    @Test
    void 이미_출석한_경우_예외가_발생한다() {
        // Given
        LocalDateTime attendanceTime = makeAttendanceTime();
        crewHistory.add(attendanceTime);

        // When & Then
        assertThatThrownBy(() -> crewHistory.add(attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 출석했습니다. 수정 기능을 이용해주세요.");
    }
}
