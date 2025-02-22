package attendance.service;

import attendance.domain.Crews;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CrewsServiceTest {

    @Test
    void 크루를_초기화한다() {
        CrewsService crewsService = new CrewsService();
        LocalDate now = LocalDate.of(2024, 12, 17);
        assertThat(crewsService.init(Map.of(
                "쿠키", List.of(LocalDateTime.of(2024, 12, 14, 9, 59))
        ), now)).isInstanceOf(Crews.class);
    }
}