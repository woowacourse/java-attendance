package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CrewTest {
    @Test
    void 같은_nickname_멤버를_가지면_같은_객체로_취급한다() {
        Crew crew1 = new Crew("에드");

        Crew crew2 = new Crew("에드");

        Crew crew3 = new Crew("제프");

        assertThat(crew1).isEqualTo(crew2);
        assertThat(crew1).isNotEqualTo(crew3);
    }

    @Test
    void 같은_nickname_멤버를_가지면_같은_해쉬코드를_반환한다() {
        Crew crew1 = new Crew("에드");

        Crew crew2 = new Crew("에드");

        Crew crew3 = new Crew("제프");

        assertThat(crew1.hashCode()).isEqualTo(crew2.hashCode());
        assertThat(crew1.hashCode()).isNotEqualTo(crew3.hashCode());
    }

}