import static org.assertj.core.api.Assertions.assertThat;

import domain.CsvReader;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CsvReaderTest {

    @Test
    @DisplayName("존재하지 않는 Csv 파일을 읽을 경우, 예외를 던진다")
    void throwExceptionWhenNotExistCsvFile() {
        //given
        String path = "notExist.csv";

        //when & then
        Assertions.assertThatThrownBy(() -> CsvReader.readFile(path));
    }
}
