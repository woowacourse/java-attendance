package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CrewTest {
    @Test
    void create_crew() {
        Crew crew = Crew.from("젠슨");
        assertThat(crew.getName()).isEqualTo("젠슨");
    }
}

