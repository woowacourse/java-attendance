import static org.assertj.core.api.Assertions.assertThat;

import infrastructure.AttendanceFileReader;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceFileReaderTest {

    @DisplayName("출석 파일을 읽어서 리스트로 반환한다.")
    @Test
    public void test1() {
        List<String> list = new AttendanceFileReader().readFile("src/main/resources/attendances.csv");

        assertThat(list.contains("쿠키,2024-12-13 10:08")).isTrue();
    }
}
