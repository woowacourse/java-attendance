import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FileTest {

    @Test
    public void test1() {
        List<String> list=File.readFile("src/main/resources/attendances.csv");

        assertThat(list.get(0)).isEqualTo("쿠키,2024-12-13 10:08");
    }
}
