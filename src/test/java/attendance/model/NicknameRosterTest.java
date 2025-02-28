package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("명단 테스트")
class NicknameRosterTest {

    @DisplayName("닉네임들을 전달하여 명단을 생성할 수 있다.")
    @Test
    void createTest() {
        // given
        Nickname bello = new Nickname("벨로");
        Nickname neo = new Nickname("네오");
        Nickname brown = new Nickname("브라운");
        List<Nickname> nicknames = List.of(bello, neo, brown);

        // when & then
        assertThatCode(() -> new NicknameRoster(nicknames))
                .doesNotThrowAnyException();
    }

    @DisplayName("명단의 닉네임은 중복될 수 없다.")
    @Test
    void duplicateTest() {
        // given
        Nickname bello = new Nickname("벨로");
        Nickname neo = new Nickname("네오");
        List<Nickname> nicknames = List.of(bello, bello, neo);
        NicknameRoster nicknameRoster = new NicknameRoster(nicknames);

        // when & then
        assertThat(nicknameRoster.getNicknames())
                .hasSize(2);
    }

    @DisplayName("특정 닉네임이 명단에 존재하는지 확인할 수 있다.")
    @Test
    void findTest() {
        // given
        Nickname bello = new Nickname("벨로");
        Nickname neo = new Nickname("네오");
        List<Nickname> nicknames = List.of(bello);
        NicknameRoster nicknameRoster = new NicknameRoster(nicknames);

        // when & then
        assertThat(nicknameRoster.isMissing(bello))
                .isFalse();
        assertThat(nicknameRoster.isMissing(neo))
                .isTrue();
    }
}
