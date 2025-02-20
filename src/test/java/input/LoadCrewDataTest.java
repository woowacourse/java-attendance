package input;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.Crew;
import attendance.model.CrewDataLoader;
import attendance.model.Crews;
import attendance.model.CustomLocalDateTime;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoadCrewDataTest {

    private Crews crews;
    private CrewDataLoader loader;

    @BeforeEach
    void setUp() {
        crews = new Crews();
        loader = new CrewDataLoader(crews, CustomLocalDateTime.now()); //12월 16일
    }

    @Test
    void 파일의_출석정보를_불러온다() {
        loader.load("attendances.csv");
        assertThat(crews.findCrew("빙티").getAttendanceHistory().stream())
                .hasSize(10);
    }

    @Test
    void Crews_데이터가_크루인원대로_생성된다() {
        loader.load("attendances.csv");

        assertThat(crews.getCrews()).hasSize(5);
    }

    @Test
    void 출석_기록이_없으면_결석처리_추가() {
        loader.load("attendances.csv");

        Crew crew = crews.findCrew("빙티");

        assertThat(crew.getAttendanceHistory()
                .containsDate(LocalDate.of(2024, 12, 9))
        ).isTrue();
    }

}
