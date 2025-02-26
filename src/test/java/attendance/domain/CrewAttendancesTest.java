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

    @Test
    void 크루의_지정한_날짜_출석_기록을_조회한다() {
        CrewAttendances crewAttendances = new CrewAttendances(crewAttendanceDateTimes);
        Crew crew = new Crew("빙봉");

        Attendance findAttendance = crewAttendances.findCrewAttendanceByLocalDate(crew, LocalDate.of(2025, 2, 26));

        assertThat(findAttendance).isEqualTo(new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0)));
    }

    @Test
    void 존재하지_않는_크루의_출석_기록을_조회할_수_없다() {
        CrewAttendances crewAttendances = new CrewAttendances(crewAttendanceDateTimes);
        Crew crew = new Crew("비보");

        assertThatThrownBy(() -> crewAttendances.findCrewAttendanceByLocalDate(crew, LocalDate.of(2025, 2, 26)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 크루의_출석_기록이_존재하지_않는_날짜의_출석_기록을_조회할_수_없다() {
        CrewAttendances crewAttendances = new CrewAttendances(crewAttendanceDateTimes);
        Crew crew = new Crew("빙봉");

        assertThatThrownBy(() -> crewAttendances.findCrewAttendanceByLocalDate(crew, LocalDate.of(2025, 2, 25)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 크루와_수정_알자를_알려주면_해당_날짜의_출석_기록을_수정한다() {
        LocalDateTime modificationDateTime = LocalDateTime.of(2025, 2, 26, 9, 50);
        LocalDate today = LocalDate.of(2025, 2, 27);
        CrewAttendances crewAttendances = new CrewAttendances(crewAttendanceDateTimes);
        Crew crew = new Crew("빙봉");

        crewAttendances.modifyCrewAttendanceByModificationDateTime(crew, modificationDateTime, today);

        assertThat(crewAttendances.findCrewAttendanceByLocalDate(crew, modificationDateTime.toLocalDate()))
                .isEqualTo(new Attendance(modificationDateTime));
    }

    @Test
    void 수정_일자가_미래의_날짜이면_출석_기록을_수정할_수_없다() {
        LocalDateTime modificationDateTime = LocalDateTime.of(2025, 2, 28, 9, 50);
        LocalDate today = LocalDate.of(2025, 2, 27);
        CrewAttendances crewAttendances = new CrewAttendances(crewAttendanceDateTimes);
        Crew crew = new Crew("빙봉");

        assertThatThrownBy(
                () -> crewAttendances.modifyCrewAttendanceByModificationDateTime(crew, modificationDateTime, today)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("2월 27일 보다 미래의 날짜를 수정할 수 없습니다.");
    }


}
