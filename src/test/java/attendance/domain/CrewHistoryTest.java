package attendance.domain;

import static attendance.fixture.TestFixture.makeCrewHistory;
import static attendance.fixture.TestFixture.makeDecemberDate;
import static attendance.fixture.TestFixture.makeDefaultAttendanceTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
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
        LocalDateTime attendanceTime = makeDefaultAttendanceTime();

        // When
        crewHistory.add(attendanceTime);

        // Then
        assertThat(crewHistory).isEqualTo(makeCrewHistory(attendanceTime));
    }

    @Test
    void 이미_출석한_경우_예외가_발생한다() {
        // Given
        LocalDateTime attendanceTime = makeDefaultAttendanceTime();
        crewHistory.add(attendanceTime);

        // When & Then
        assertThatThrownBy(() -> crewHistory.add(attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 출석했습니다. 수정 기능을 이용해주세요.");
    }

    @Test
    void 기록이_존재하는지_확인한다() {
        // Given
        LocalDate attendanceDate = makeDecemberDate(3);

        // When & Then
        assertThatCode(() -> crewHistory.validateNotExists(attendanceDate))
                .doesNotThrowAnyException();
    }

    @Test
    void 기록이_이미_존재할_경우_예외를_발생시킨다() {
        // Given
        LocalDateTime attendanceTime = makeDefaultAttendanceTime();
        crewHistory.add(attendanceTime);
        LocalDate attendanceDate = LocalDate.from(attendanceTime);

        // When & Then
        assertThatThrownBy(() -> crewHistory.validateNotExists(attendanceDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 이미 출석했습니다.");
    }
}
