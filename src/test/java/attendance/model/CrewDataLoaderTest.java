package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CrewDataLoaderTest {

    private Crews crews;
    private CrewDataLoader loader;

    @BeforeEach
    void setUp() {
        crews = new Crews();
        loader = new CrewDataLoader(crews, CustomLocalDateTime.now()); //12월 16일
    }

    @Test
    void 파일의_출석정보를_출석정보개수만큼_불러온다() {
        //given & when
        loader.load("attendances.csv");

        //then
        assertThat(crews.findCrew("빙티").getAttendanceHistory().stream())
                .hasSize(10);
    }

    @Test
    void Crews_데이터가_크루인원대로_생성된다() {
        //given & when
        loader.load("attendances.csv");

        //then
        assertThat(crews.getCrews()).hasSize(5);
    }

    @Test
    void 출석_기록이_없으면_결석처리_추가() {
        //given & when
        loader.load("attendances.csv");
        Crew crew = crews.findCrew("빙티");

        //then
        assertThat(crew.getAttendanceHistory()
                .containsDate(LocalDate.of(2024, 12, 9))
        ).isTrue();
    }

}