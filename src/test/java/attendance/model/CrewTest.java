package attendance.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {
    @DisplayName("이름이 같은 서로 다른 크루객체는 동일 크루로 취급한다.")
    @Test
    void test() {
        Crew crew1 = new Crew("엠제이");
        Crew crew2 = new Crew("엠제이");

        assertEquals(crew1, crew2);
    }
}
