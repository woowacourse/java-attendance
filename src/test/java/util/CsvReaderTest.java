package util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CsvReaderTest {

    private List<String[]> csvLines;

    @BeforeEach
    void setUp() throws IOException {
        // given
        Path csvFilePath = Paths.get("src", "main", "resources", "attendances.csv");
        // when
        csvLines = CsvReader.readCsvLines(csvFilePath.toString());
    }

    @Test
    @DisplayName("CSV 파일 데이터 로딩 기능 테스트")
    void CSV_파일_데이터_로딩_기능_테스트() throws IOException {
        // then
        assertNotNull(csvLines, "CSV 파일 데이터가 null이어서는 안 됩니다.");
        assertEquals(46, csvLines.size(), "CSV 파일의 라인 수가 예상과 다릅니다.");
    }

    @Test
    @DisplayName("CSV 파일 데이터 헤더 파싱 기능 테스트")
    void CSV_파일_데이터_헤더_파싱_기능_테스트() throws IOException {
        // then
        String[] header = csvLines.getFirst();
        assertEquals(2, header.length, "헤더의 컬럼 수가 예상과 다릅니다.");
        assertEquals("nickname", header[0], "첫 번째 컬럼은 'nickname'이어야 합니다.");
        assertEquals("datetime", header[1], "두 번째 컬럼은 'datetime'이어야 합니다.");
    }

    @Test
    @DisplayName("CSV 파일 데이터 데이터 파싱 기능 테스트")
    void CSV_파일_데이터_데이터_파싱_기능_테스트() throws IOException {
        // then
        String[] firstDataRow = csvLines.get(1);
        assertEquals(2, firstDataRow.length, "첫 번째 데이터의 컬럼 수가 예상과 다릅니다.");
        assertEquals("쿠키", firstDataRow[0], "첫 번째 데이터의 닉네임이 예상과 다릅니다.");
        assertEquals("2024-12-13 10:08", firstDataRow[1], "첫 번째 데이터의 날짜 및 시간이 예상과 다릅니다.");
    }
}

