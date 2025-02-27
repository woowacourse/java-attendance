import static org.assertj.core.api.Assertions.assertThat;

import domain.Crews;
import infrastructure.AttendanceFileReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceFileReaderTest {

    @DisplayName("출석 파일을 읽어서 리스트로 반환한다.")
    @Test
    public void test1() {
        Crews crews = new AttendanceFileReader().readFile("src/main/resources/attendances.csv");

        assertThat(crews.findByNickname("쿠키").getName()).isEqualTo("쿠키");
    }
}
