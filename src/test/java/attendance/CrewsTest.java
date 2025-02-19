package attendance;

import attendance.domain.Attendance;
import attendance.domain.Crew;
import attendance.domain.Crews;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CrewsTest {

    @Test
    void 닉네임을_이용해_크루를_검색한다() {
        List<Crew> crewsList = List.of(new Crew("쿠키",
                List.of(new Attendance(LocalDateTime.of(2024, 12, 16, 9, 59)))));
        Crews crews = new Crews(crewsList);
        assertThat(crews.findByName("쿠키")).extracting("nickname")
                .isEqualTo("쿠키");
    }
}

