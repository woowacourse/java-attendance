package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewStorageTest {

    CrewStorage crewStorage;

    @BeforeEach
    void beforeEach() {
        crewStorage = new CrewStorage();
    }

    @DisplayName("크루를 추가할 수 있다.")
    @Test
    void 크루를_추가할_수_있다() {
        Crew newCrew = new Crew("쿠키");

        crewStorage.add(newCrew);

        boolean isContained = crewStorage.isContained(newCrew.getName());
        assertThat(isContained).isTrue();
    }

    @DisplayName("이름을 통해 크루가 등록된 크루인지 확인할 수 있다")
    @Test
    void 이름을_통해_크루가_등록된_크루인지_확인할_수_있다() {
        String isContainedName = "쿠키";
        String isNotContainedName = "빙봉";
        crewStorage.add(new Crew(isContainedName));

        assertThat(crewStorage.isContained(isContainedName)).isTrue();
        assertThat(crewStorage.isContained(isNotContainedName)).isFalse();
    }
}