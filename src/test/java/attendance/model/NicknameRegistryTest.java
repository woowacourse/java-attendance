package attendance.model;

import static attendance.model.TestFixtures.BELLO_NICKNAME;
import static attendance.model.TestFixtures.BROWN_NICKNAME;
import static attendance.model.TestFixtures.NEO_NICKNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("닉네임 등록 명단 테스트")
class NicknameRegistryTest {

    @DisplayName("닉네임들을 전달하여 명단을 생성할 수 있다.")
    @Test
    void createTest() {
        // given
        List<Nickname> nicknames = List.of(BELLO_NICKNAME, NEO_NICKNAME, BROWN_NICKNAME);

        // when & then
        assertThatCode(() -> new NicknameRegistry(nicknames))
                .doesNotThrowAnyException();
    }

    @DisplayName("명단의 닉네임은 중복될 수 없다.")
    @Test
    void shouldNotDuplicateNicknames() {
        // given
        List<Nickname> nicknames = List.of(BELLO_NICKNAME, BELLO_NICKNAME);
        NicknameRegistry nicknameRegistry = new NicknameRegistry(nicknames);

        // when & then
        assertThat(nicknameRegistry.getNicknames())
                .hasSize(1);
    }

    @DisplayName("특정 닉네임이 명단에 등록되어 있는지 확인할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "벨로, true",
            "네오, false",
    })
    void isMissingTest(String nickname, boolean expected) {
        // given
        List<Nickname> nicknames = List.of(BELLO_NICKNAME);
        NicknameRegistry nicknameRegistry = new NicknameRegistry(nicknames);

        // when & then
        assertThat(nicknameRegistry.isRegistered(new Nickname(nickname)))
                .isEqualTo(expected);
    }
}
