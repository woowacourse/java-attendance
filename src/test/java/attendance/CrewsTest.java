package attendance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
}
