package attendance.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class CrewsTest {

    @Test
    void 크루를_초기화한다() {
        LocalDate now = LocalDate.of(2024, 12, 17);

        assertThat(Crews.init(Map.of(
                "쿠키", List.of(LocalDateTime.of(2024, 12, 14, 9, 59))
        ), now)).isInstanceOf(Crews.class);
    }

    @Test
    void 닉네임을_이용해_크루를_검색한다() {
        List<Crew> crewsList = List.of(new Crew("쿠키",
                new Attendances(LocalDate.of(2024,12,11), List.of(LocalDateTime.of(2024, 12, 16, 9, 59)))));
        Crews crews = new Crews(crewsList);
        assertThat(crews.findByName("쿠키")).extracting("nickname")
                .isEqualTo("쿠키");
    }

    @Test
    void 닉네임이_없으면_예외() {
        List<Crew> crewsList = List.of(new Crew("쿠키",
                new Attendances(LocalDate.of(2024,12,11), List.of(LocalDateTime.of(2024, 12, 16, 9, 59)))));
        Crews crews = new Crews(crewsList);
        assertThatThrownBy(() -> crews.findByName("훌라")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 제적_위험_대상자_테스트() {
        List<Attendance> attendance = List.of(new Attendance(LocalDateTime.of(2024, 12, 16, 13, 7)),
                new Attendance(LocalDateTime.of(2024, 12, 17, 10, 7)),
                new Attendance(LocalDateTime.of(2024, 12, 18, 10, 7)),
                new Attendance(LocalDateTime.of(2024, 12, 20, 11, 7)));
        Attendances attendances = new Attendances(LocalDate.of(2024, 12, 11), List.of(LocalDateTime.of(2024, 12, 16, 9, 59)));

        Crew crew1 = new Crew("훌라", attendances);
        Crew crew2 = new Crew("모루", attendances);

        Crews crews = new Crews(List.of(crew1, crew2));

        assertThat(crews.collectWarningCrews().size()).isEqualTo(2);
    }
}

