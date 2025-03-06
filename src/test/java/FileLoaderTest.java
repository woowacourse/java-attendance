import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendanceBook;
import loader.FileLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class FileLoaderTest {

    @DisplayName("12월 출석 기록 파일(attendances.csv)을 읽어 객체를 생성할 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"빙봉", "이든", "쿠키", "빙티", "짱수"})
    void should_CreateObject_When_givenData(String name) {
        String filePath = "src/main/resources/attendances.csv";
        AttendanceBook attendanceBook = new AttendanceBook();
        FileLoader fileReader = new FileLoader(attendanceBook);

        fileReader.loadFile(filePath);

        assertThat(attendanceBook.findCrewByName(name).getName()).isEqualTo(name);
    }
}
