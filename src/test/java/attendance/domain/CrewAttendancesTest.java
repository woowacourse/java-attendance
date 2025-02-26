package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CrewAttendancesTest {

    private final Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes = Map.of(new Crew("빙봉"), List.of(
            LocalDateTime.of(2025, 2, 26, 10, 0)
    ));

    @Test
    void 전체_크루의_모든_출석_기록을_저장한다() {
        assertDoesNotThrow(() -> new CrewAttendances(crewAttendanceDateTimes));
    }

    @CsvSource(value = {
            "26,true", "27,false"
    })
    @ParameterizedTest
    void 크루와_날짜를_알려주면_해당_출석_기록의_존재_여부를_알려준다(int day, boolean expected) {
        CrewAttendances crewAttendances = new CrewAttendances(crewAttendanceDateTimes);
        Crew crew = new Crew("빙봉");

        assertThat(crewAttendances.hasCrewAttendanceByLocalDate(crew, LocalDate.of(2025, 2, day)))
                .isEqualTo(expected);
    }

    @Test
    void 크루가_존재하지_않으면_출석_기록_존재_여부를_알려줄_수_없다() {
        CrewAttendances crewAttendances = new CrewAttendances(crewAttendanceDateTimes);
        Crew crew = new Crew("비보");

        assertThatThrownBy(() -> crewAttendances.hasCrewAttendanceByLocalDate(crew, LocalDate.of(2025, 2, 26)))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
