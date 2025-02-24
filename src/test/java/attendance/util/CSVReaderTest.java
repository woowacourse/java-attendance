package attendance.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CSVReaderTest {
    @DisplayName("csv 파일로부터 중첩 리스트 객체를 반환한다.")
    @Test
    void returnListFromCSV() {
        Path path = Paths.get("src/main/resources/attendances.csv");
        assertAll(
                () -> assertEquals(41, CSVReader.readCSV(path).size()),
                () -> assertThat(CSVReader.readCSV(path).getFirst()).isEqualTo(
                        List.of("쿠키", "2025-02-14 13:03")
                ),
                () -> assertThat(CSVReader.readCSV(path).getLast()).isEqualTo(
                        List.of("짱수", "2025-02-03 10:00")
                )
        );
    }
}