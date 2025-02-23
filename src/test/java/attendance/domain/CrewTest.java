package attendance.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {
    @DisplayName("이름이 같은 다른 크루 객체는 같은 크루로 확인한다.")
    @Test
    void checkSameNameCrew() {
        Crew crew1 = new Crew("리원");
        Crew crew2 = new Crew("리원");

        assertEquals(crew1, crew2);
    }

    @DisplayName("이름으로 Crew 인스턴스의 일치 여부를 확인한다.")
    @Test
    void checkMatchingInstance() {
        Crew crew1 = new Crew("엠제이");

        assertTrue(crew1.isSameCrewName("엠제이"));
    }
}
