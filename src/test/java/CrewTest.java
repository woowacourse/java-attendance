import static org.assertj.core.api.Assertions.assertThat;

import domain.Crew;
import domain.StringParser;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @Test
    void test1() {
        String crew1 = "쿠키,2024-12-13 10:08";

        var parsedCrew1 = StringParser.parse(crew1);

        assertThat(parsedCrew1[0]).isEqualTo("쿠키");
    }

    @Test
    void test2() {
        String[] crew1 = {"쿠키", "2024-12-13 10:08"};
        Crew parsedCrew1 = new Crew(crew1[0], crew1[1]);

        assertThat(parsedCrew1.getName()).isEqualTo("쿠키");
        assertThat(parsedCrew1.getAttendTimes().get(0).toString()).isEqualTo("2024-12-13T10:08");
    }

    @Test
    void test3() {
        Crew crew = new Crew("폰트", "2024-12-13 10:08");

        crew.attend("2024-12-13 10:09");

        assertThat(crew.getAttendTimes().size()).isEqualTo(2);
    }
}
