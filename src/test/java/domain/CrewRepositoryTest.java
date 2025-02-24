package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewRepositoryTest {

    @Test
    @DisplayName("기존에 존재하는 크루명으로 add하는 경우 기존 출석 기록에 add한 출석 기록이 추가된다")
    void addTest() {
        CrewRepository crewRepository = CrewRepository.generate();
        crewRepository.add("cube", LocalDate.of(2025, 02, 03), LocalTime.of(10, 00));
        crewRepository.add("cube", LocalDate.of(2025, 02, 04), LocalTime.of(13, 00));
        Crew found = crewRepository.get("cube");

        assertThat(found.getAttendanceTimeByDate(LocalDate.of(2025,02,03))).isEqualTo(LocalTime.of(10, 00));
        assertThat(found.getAttendanceTimeByDate(LocalDate.of(2025,02,04))).isEqualTo(LocalTime.of(13, 00));
    }
}
