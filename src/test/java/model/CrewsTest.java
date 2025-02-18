package model;

import attendance.model.Crew;
import attendance.model.Crews;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CrewsTest {

    @Test
    void test1() {
        Crews crews = new Crews();
        Assertions.assertThat(crews).isNotNull();
    }

    @Test
    void test2() {
        Crews crews = new Crews();
        Crew crew = new Crew("빙티");
        crews.add(crew);
        Assertions.assertThat(crews.getCrews()).hasSize(1);
    }
}
