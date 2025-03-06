package domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class CrewsTest {

    @Test
    void 등록되지_않은_크루를_조회할_경우_예외가_발생한다() {
        final String name = "시소";

        Crews crews = new Crews();

        assertThatThrownBy(() -> crews.validateHasCrew(name)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 등록된_크루를_조회할_경우_예외는_발생하지_않는다() {
        final String name = "시소";

        Crews crews = new Crews();
        crews.initCrew(name);

        assertThatNoException().isThrownBy(() -> crews.validateHasCrew(name));
    }
}