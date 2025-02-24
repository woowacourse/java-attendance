package attendance.domain;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class CrewAttendancesTest {

    @Test
    void 닉네임을_알려주면_해당_크루를_찾아준다() {
        Crew crew = new Crew("빙봉");
        CrewAttendances crewAttendances = new CrewAttendances(
                Map.of(crew, List.of(LocalDateTime.of(2025, 2, 18, 10, 0))),
                LocalDateTime.of(2025, 2, 19, 10, 0)
        );

        assertThat(crewAttendances.findCrewByNickname("빙봉")).isEqualTo(crew);
    }

    @Test
    void 해당_닉네임의_크루가_존재하지_않으면_찾을_수_없다() {
        Crew crew = new Crew("빙봉");
        CrewAttendances crewAttendances = new CrewAttendances(
                Map.of(crew, List.of(LocalDateTime.of(2025, 2, 18, 10, 0))),
                LocalDateTime.of(2025, 2, 19, 10, 0)
        );

        assertThatThrownBy(() -> crewAttendances.findCrewByNickname("비보"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 크루와_날짜를_알려주면_해당_출석_기록을_알려준다() {
        Crew crew = new Crew("빙봉");
        CrewAttendances crewAttendances = new CrewAttendances(
                Map.of(crew, List.of(LocalDateTime.of(2025, 2, 18, 10, 0))),
                LocalDateTime.of(2025, 2, 19, 10, 0)
        );

        assertThat(crewAttendances.findAttendanceByLocalDate(crew, of(2025, 2, 18)))
                .isEqualTo(new Attendance(
                        new AttendanceDate(LocalDate.of(2025, 2, 18)),
                        new AttendanceTime(LocalTime.of(10, 0)))
                );
    }

    @Test
    void 크루와_기존_출석_변경_시간을_알려주면_출석_기록을_수정한다() {
        Crew crew = new Crew("빙봉");
        CrewAttendances crewAttendances = new CrewAttendances(
                Map.of(crew, List.of(LocalDateTime.of(2025, 2, 18, 10, 0))),
                LocalDateTime.of(2025, 2, 19, 10, 0)
        );
        Attendance originAttendance = crewAttendances.findAttendanceByLocalDate(crew, of(2025, 2, 18));
        LocalTime modificationTime = LocalTime.of(9, 50);

        assertThat(crewAttendances.modifyAttendance(crew, originAttendance, modificationTime))
                .isEqualTo(new Attendance(
                        new AttendanceDate(LocalDate.of(2025, 2, 18)),
                        new AttendanceTime(modificationTime))
                );
    }

}
