package util.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileParserTest {

    @Test
    @DisplayName("빈 입력이 주어졌을 때 빈 map을 반환한다.")
    void successLoadEmptyFile() {
        Scanner scanner = new Scanner("");
        Map<String, List<LocalDateTime>> result = FileParser.parseScannerToMap(scanner);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("정상적인 csv 데이터를 입력했을 때 데이터를 반환한다.")
    void successLoadFile() {
        String csvData = """
            파랑,2024-03-01 08:00
            사나,2024-03-01 09:00
            파랑,2024-03-02 10:00
            """;
        Scanner scanner = new Scanner(csvData);
        Map<String, List<LocalDateTime>> result = FileParser.parseScannerToMap(scanner);

        assertAll(
            () -> assertThat(result).hasSize(2),
            () -> assertThat(result).containsKeys("파랑", "사나"),
            () -> assertThat(result.get("파랑")).containsExactly(
                DateTimeParser.parseStringToDateTime("2024-03-01 08:00"),
                DateTimeParser.parseStringToDateTime("2024-03-02 10:00")),
            () -> assertThat(result.get("사나")).containsExactly(
                DateTimeParser.parseStringToDateTime("2024-03-01 09:00"))
        );
    }
}