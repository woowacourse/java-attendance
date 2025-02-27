package attendance.domain.crew;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.exception.AttendanceException;
import attendance.exception.ExceptionMessage;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewStorageTest {

    CrewStorage crewStorage = new CrewStorage();

    @DisplayName("새로운 크루를 추가할 수 있다")
    @Test
    void 새로운_크루를_추가할_수_있다() {
        String nickname = "쿠키";
        crewStorage.add(nickname);

        assertThat(crewStorage.checkIsNotContained(nickname)).isFalse();
    }

    @DisplayName("모든 크루의 닉네임을 조회할 수 있다")
    @Test
    void 모든_크루의_닉네임을_조회할_수_있다() {
        List<String> nicknames = List.of("쿠키", "빙봉", "이든", "인트");
        nicknames.forEach(nickname -> crewStorage.add(nickname));

        List<String> savedCrewNicknames = crewStorage.findAllNicknames();
        assertThat(savedCrewNicknames).containsExactlyInAnyOrderElementsOf(nicknames);
    }

    @DisplayName("해당 이름의 크루가 존재하지 않는 것을 검증할 수 있다")
    @Test
    void 해당_이름의_크루가_존재하지_않는_것을_검증할_수_있다() {
        crewStorage.add("쿠키");

        assertThatCode(() -> crewStorage.validateIsNotContained("쿠키"))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> crewStorage.validateIsNotContained("빙봉"))
                .isInstanceOf(AttendanceException.class)
                .hasMessage(ExceptionMessage.INVALID_CREW.getMessage());
    }

    @DisplayName("해당 이름의 크루가 존재하는지 체크할 수 있다")
    @Test
    void 해당_이름의_크루가_존재하는지_체크할_수_있다() {
        crewStorage.add("쿠키");
        assertThat(crewStorage.checkIsNotContained("쿠키")).isFalse();
        assertThat(crewStorage.checkIsNotContained("빙봉")).isTrue();
    }

}