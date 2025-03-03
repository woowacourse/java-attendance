import domain.CsvReader;
import java.util.List;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CsvReaderTest {

    @Test
    @DisplayName("Csv 파일을 각 행을 String으로 읽어온다")
    void readCsvFile() {
        //given
        String path = "attendances.csv";

        //when
        List<String> actual = CsvReader.readFile(path);

        //then
        List<String> expected = List.of(
                "nickname,datetime",
                "빙티,2024-12-02 10:00", "빙티,2024-12-03 10:00", "빙티,2024-12-05 10:00",
                "가나,2024-12-02 10:05", "가나,2024-12-03 10:06", "가나,2024-12-05 10:31"
        );
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Csv 파일을 각 행을 String으로 읽고 첫 행을 없앤다.")
    void readCsvFileWithoutFirstRow() {
        //given
        String path = "attendances.csv";

        //when
        List<String> actual = CsvReader.readFile(path);
        CsvReader.removeFirstRow(actual);

        //then
        List<String> expected = List.of(
                "빙티,2024-12-02 10:00", "빙티,2024-12-03 10:00", "빙티,2024-12-05 10:00",
                "가나,2024-12-02 10:05", "가나,2024-12-03 10:06", "가나,2024-12-05 10:31"
        );
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("존재하지 않는 Csv 파일을 읽을 경우, 예외를 던진다")
    void throwExceptionWhenNotExistCsvFile() {
        //given
        String path = "notExist.csv";

        //when & then
        Assertions.assertThatThrownBy(() -> CsvReader.readFile(path));
    }

    @Test
    @DisplayName("하나의 행을 분리한다")
    void splitRow() {
        //given
        String path = "attendances.csv";
        List<String> lines = CsvReader.readFile(path);
        CsvReader.removeFirstRow(lines);
        String row = lines.getFirst();

        //when
        List<String> actual = CsvReader.splitRow(row);

        //then
        List<String> expected = List.of("빙티", "2024-12-02 10:00");
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("잘못된 형식의 행을 분리할 경우, 예외를 던진다")
    void throwExceptionWhenWrongFormatCsvFile() {
        //given
        String path = "wrong_format.csv";
        List<String> lines = CsvReader.readFile(path);
        CsvReader.removeFirstRow(lines);

        //when & then
        Assertions.assertThatThrownBy(() -> CsvReader.splitRow(lines.getFirst()));
    }
}
