import static org.assertj.core.api.Assertions.assertThat;

import domain.Crew;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceFileReaderTest {

    @DisplayName("12월 출석 기록 파일(attendances.csv)을 읽어 객체를 생성할 수 있다.")
    @Test
    void should_CreateObject_When_givenData() {
        String filePath = "src/main/resources/attendances.csv";
        AttendanceFileReader fileReader = new AttendanceFileReader();

        List<Crew> crews = fileReader.loadFile(filePath);

        assertThat(crews.size()).isEqualTo(41);
    }
}
