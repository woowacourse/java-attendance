import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendanceFileReader;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AttendanceFileReaderTest {

    @Test
    public void test1() {
        List<String> list = AttendanceFileReader.readFile("src/main/resources/attendances.csv");

        assertThat(list.get(0)).isEqualTo("쿠키,2024-12-13 10:08");
    }
}
