package domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class CrewsTest {
    @Test
    void 이름과_날짜로_출석을_저장한다() {
        LocalDate localDate = LocalDate.of(2024, 12, 10);
        LocalTime localTime = LocalTime.of(10, 4);

        Crews crews = new Crews();
        crews.addCrew("두리");
        Crew crew = crews.findCrewByName("두리");
        crew.addAttendStatus(LocalDateTime.of(localDate, localTime));

        assertThat(crews.getAttendanceTime("두리", localDate)).isEqualTo(localTime);
    }

    @Test
    void 이름과_날짜로_출석을_수정한다() {
        LocalDate localDate = LocalDate.of(2024, 12, 10);
        LocalTime localTime = LocalTime.of(10, 04);

        Crews crews = new Crews();
        crews.addCrew("두리");
        Crew crew = crews.findCrewByName("두리");
        crew.addAttendStatus(LocalDateTime.of(localDate, LocalTime.of(10, 50)));
        crew.editAttendStatus(LocalDateTime.of(localDate, localTime));

        assertThat(crews.getAttendanceTime("두리", localDate)).isEqualTo(localTime);
    }

    @Test
    void 해당_크루_이름이_있으면_true를_반환한다() {
        Crews crews = new Crews();
        crews.addCrew("두리");

        assertThat(crews.hasCrewName("두리")).isEqualTo(true);
        assertThat(crews.hasCrewName("시소")).isEqualTo(false);

    }
}
