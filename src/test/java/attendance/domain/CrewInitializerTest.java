package attendance.domain;

import static attendance.fixture.TestFixture.makeAbsenceExceptMonday;
import static attendance.fixture.TestFixture.makeAbsenceMonday;
import static attendance.fixture.TestFixture.makeAttendanceExceptMonday;
import static attendance.fixture.TestFixture.makeAttendanceMonday;
import static attendance.fixture.TestFixture.makeCrewHistory;
import static attendance.fixture.TestFixture.makeTardinessExceptMonday;
import static attendance.fixture.TestFixture.makeTardinessMonday;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CrewInitializerTest {

    @Test
    void 데이터를_통해_크루_기록을_초기화한다() {
        // Given
        Map<String, List<LocalDateTime>> histories = Map.of(
                "빙봉", List.of(makeAttendanceMonday(2), makeAttendanceExceptMonday(3)),
                "빙티", List.of(makeTardinessMonday(2), makeTardinessExceptMonday(3)),
                "짱수", List.of(makeAbsenceMonday(2), makeAbsenceExceptMonday(3))
        );
        CrewInitializer crewInitializer = new CrewInitializer(histories);

        // When
        CrewHistories crewHistories = crewInitializer.initialize();

        // Then
        assertThat(crewHistories).isEqualTo(new CrewHistories(Map.of(
                new Nickname("빙봉"), makeCrewHistory(makeAttendanceMonday(2), makeAttendanceExceptMonday(3)),
                new Nickname("빙티"), makeCrewHistory(makeTardinessMonday(2), makeTardinessExceptMonday(3)),
                new Nickname("짱수"), makeCrewHistory(makeAbsenceMonday(2), makeAbsenceExceptMonday(3))
        )));
    }
}
