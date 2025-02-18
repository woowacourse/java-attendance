import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

public class StudentTest {

    @Test
    void test1() {
        String crew1 = "쿠키,2024-12-13 10:08";

        var parsedCrew1 = StringParser.parse(crew1);

        assertThat(parsedCrew1[0]).isEqualTo("쿠키");
    }
}
