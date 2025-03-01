package util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {
    private static final String CSV_FILE_PATH = "src/test/resources/attendances.csv";

    @Test
    @DisplayName("파일 이름을 통해 해당 파일의 값을 List<String[]>형태로 읽음")
    void readCsvFileTest() throws IOException {
        // when
        List<String[]> read = CsvFileReader.readCsvFile(CSV_FILE_PATH);

        // then
        assertThat(read).contains(new String[]{"차니", "2024-12-02 13:00"})
                .contains(new String[]{"포비", "2024-12-02 13:06"})
                .hasSize(2);
    }
}
