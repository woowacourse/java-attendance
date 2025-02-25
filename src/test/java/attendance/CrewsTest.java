package attendance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewsTest {

    @Test
    @DisplayName("크루의 닉네임이 들어오면, 크루를 추가하고, Crew를 리턴한다.")
    void addCrewByNicknameTest1() {
        Crews crews = new Crews();
        assertThat(crews.addCrew("모루")).isInstanceOf(Crew.class);
    }

    @Test
    @DisplayName("이미 추가된 크루면 예외")
    void addCrewByNicknameTest2() {
        Crews crews = new Crews();
        crews.addCrew("모루");
        assertThatThrownBy(() -> crews.addCrew("모루")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이미 존재하는 크루일 경우, 닉네임을 통해 크루를 찾는다")
    void findCrewByNicknameTest1() {
        Crews crews = new Crews();
        crews.addCrew("모루");
        assertThat(crews.findCrewByNickname("모루")).hasFieldOrPropertyWithValue("nickname", "모루");
    }

    @Test
    @DisplayName("찾으려는 크루가 없을 경우 예외")
    void findCrewByNicknameTest2() {
        Crews crews = new Crews();
        crews.addCrew("모루");
        assertThatThrownBy(() -> crews.findCrewByNickname("히포")).isInstanceOf(IllegalArgumentException.class);
    }
}
