package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import attendance.exception.ExceptionMessage;
import java.util.List;
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

    @DisplayName("모든 크루를 조회할 수 있다.")
    @Test
    void 모든_크루를_조회할_수_있다() {
        List<String> nicknames = List.of("쿠키1", "쿠키2", "쿠키3");
        nicknames.forEach(nickname -> crewStorage.add(new Crew(nickname)));

        List<Crew> allCrew = crewStorage.findAll();
        assertThat(allCrew)
                .extracting(Crew::getName)
                .containsExactlyInAnyOrder("쿠키1", "쿠키2", "쿠키3");
    }

    @DisplayName("등록된 크루인지 검증할 수 있다.")
    @Test
    void 등록된_크루인지_검증할_수_있다() {
        crewStorage.add(new Crew("쿠키"));

        assertThatCode(() -> crewStorage.validateCrew("쿠키"))
                .doesNotThrowAnyException();
        assertThatIllegalArgumentException()
                .isThrownBy(() -> crewStorage.validateCrew("빙봉"))
                .withMessage(ExceptionMessage.NOT_FOUND_CREW.getContent());
    }
}