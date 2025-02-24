package util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CsvReaderTest {

    @Test
    void 파일을_정상적으로_가져온다() {
        List<String[]> parsedResult = CsvReader.readFile("src/main/resources/attendances.csv");

        Map<String, String> attendances = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            attendances.put(parsedResult.get(i)[0], parsedResult.get(i)[1]);
        }
        Map<String, String> expectedResult = new HashMap<>();
        expectedResult.put("쿠키", "2024-12-13 10:08");
        expectedResult.put("빙봉", "2024-12-13 10:07");
        expectedResult.put("빙티", "2024-12-13 10:07");
        expectedResult.put("이든", "2024-12-13 10:07");

        for (String nickname : expectedResult.keySet()) {
            String localDateTime = attendances.get(nickname);
            Assertions.assertThat(localDateTime).isEqualTo(expectedResult.get(nickname));
        }
    }
}
