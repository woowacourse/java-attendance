package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.crew.Crews;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CrewsTest {

    @ParameterizedTest
    @ValueSource(strings = {"히스타", "히로"})
    @DisplayName("존재하지 않는 크루를 이름으로 찾는 경우 예외를 던진다")
    void testFindCrewByThrowsException(String nickname) {
        Crews crews = new Crews();
        assertThatIllegalArgumentException()
                .isThrownBy(() -> crews.findCrewBy(nickname))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이름으로 크루를 정상적으로 찾아온다")
    void testFindCrew() {
        // given
        Crews crews = new Crews();

        String name = "히로";
        crews.add(name);

        // when & then
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> crews.findCrewBy(name)),
                () -> assertThat(crews.findCrewBy(name).getName()).isEqualTo(name)
        );
    }
}
