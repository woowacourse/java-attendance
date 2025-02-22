package attendance.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CrewTest {

    @DisplayName("크루가 같은 닉네임을 가졌는지 확인할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "neo, neo, true",
            "neo, pobi, false"
    })
    void hasEqualsNicknameTest(String crewNickname, String checkNickname, boolean expected) {
        Crew neo = new Crew(new Nickname(crewNickname));

        Assertions.assertThat(neo.isEqualsNickname(new Nickname(checkNickname)))
                .isEqualTo(expected);
    }

    @DisplayName("같은 닉네임을 가진 크루는 동등해야한다.")
    @ParameterizedTest
    @CsvSource({
            "neo, neo, true",
            "neo, pobi, false"
    })
    void equalsTest(String crewNickname, String checkNickname, boolean expected) {
        Crew neo = new Crew(new Nickname(crewNickname));

        Assertions.assertThat(neo.equals(new Crew(new Nickname(checkNickname))))
                .isEqualTo(expected);
    }
}
