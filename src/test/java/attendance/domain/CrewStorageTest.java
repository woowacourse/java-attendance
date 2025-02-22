package attendance.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
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
        String newCrew = "쿠키";
        crewStorage.addCrew(newCrew);

        assertThat(crewStorage.isContained(newCrew)).isTrue();
    }

    @DisplayName("이름을 통해 크루가 등록된 크루인지 확인할 수 있다")
    @Test
    void 이름을_통해_크루가_등록된_크루인지_확인할_수_있다() {
        crewStorage.addCrew("쿠키");
        crewStorage.addCrew("빙봉");

        assertThat(crewStorage.isContained("쿠키")).isTrue();
        assertThat(crewStorage.isContained("이든")).isFalse();
    }
}