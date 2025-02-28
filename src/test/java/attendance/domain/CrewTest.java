package attendance.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.Test;

public class CrewTest {

    @Test
    public void 크루_생성() {
        //given
        String name = "우가";

        //when & then
        assertDoesNotThrow(() -> new Crew(name));
    }
}
