package attendance.model.loader;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.Crew;
import attendance.model.Crews;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewRegistryTest {

    @Test
    @DisplayName("이미 등록된 크루가 있으면 해당 크루를 반환한다")
    void returnsExistingCrew() {
        // given
        Crews crews = new Crews(new ArrayList<>());
        CrewRegistry registry = new CrewRegistry(crews);
        Crew existingCrew = new Crew("빙티");
        crews.add(existingCrew);

        // when
        Crew foundCrew = registry.findCrewOrCreate("빙티");

        // then
        assertThat(foundCrew).isSameAs(existingCrew);
    }

    @Test
    @DisplayName("등록되지 않은 크루의 경우 새 크루를 생성하여 등록하고 반환한다")
    void createsAndRegistersCrewIfNotExists() {
        // given
        Crews crews = new Crews(new ArrayList<>());
        CrewRegistry registry = new CrewRegistry(crews);

        // when
        Crew foundCrew = registry.findCrewOrCreate("빙티");

        // then
        assertThat(foundCrew.getName()).isEqualTo("빙티");
        assertThat(crews.getCrews()).contains(foundCrew);
    }

    @Test
    @DisplayName("동일한 크루 이름에 대해 두 번 요청하면 동일한 Crew 객체를 반환한다")
    void returnsSameInstanceForSameCrewName() {
        // given
        Crews crews = new Crews(new ArrayList<>());
        CrewRegistry registry = new CrewRegistry(crews);

        // when
        Crew crew1 = registry.findCrewOrCreate("빙티");
        Crew crew2 = registry.findCrewOrCreate("빙티");

        // then
        assertThat(crew1).isSameAs(crew2);
        assertThat(crews.getCrews()).hasSize(1);
    }
}
