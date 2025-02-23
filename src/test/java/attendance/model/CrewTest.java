package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CrewTest {

    @DisplayName("크루가 같은 닉네임을 가졌는지 확인할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "네오, true",
            "포비, false"
    })
    void hasEqualsNicknameTest(String checkNickname, boolean expected) {
        // given
        Crew neo = AttendanceTestFixtures.NEO;

        // when & then
        assertThat(neo.isEqualsNickname(new Nickname(checkNickname)))
                .isEqualTo(expected);
    }

    @DisplayName("같은 닉네임을 가진 크루는 동등해야한다.")
    @ParameterizedTest
    @CsvSource({
            "네오, true",
            "포비, false"
    })
    void equalsTest( String checkNickname, boolean expected) {
        // given
        Crew neo = AttendanceTestFixtures.NEO;

        // when & then
        assertThat(neo.equals(new Crew(new Nickname(checkNickname))))
                .isEqualTo(expected);
    }
}
