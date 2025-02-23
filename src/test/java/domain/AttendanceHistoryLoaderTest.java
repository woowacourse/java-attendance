package domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryLoaderTest {

    private File createTempCsvFile(String csvData) throws IOException {
        Path tempFile = Files.createTempFile("test_attendance", ".csv");
        Files.write(tempFile, csvData.getBytes());

        return tempFile.toFile();
    }

    @Test
    void 유효한_형식의_파일에_대해_Crew를_정상적으로_초기화한다() throws Exception {
        String csvData = "nickname,datetime\n에드,2025-02-13 08:30\n제프,2025-02-14 09:15\n";
        File tempCsvFile = createTempCsvFile(csvData);

        AttendanceHistoryLoader loader = new AttendanceHistoryLoader();
        FileReader reader = new FileReader(tempCsvFile);

        Crews crews = loader.loadCrews(reader);

        assertNotNull(crews.findByNickname("에드"));
        assertNotNull(crews.findByNickname("제프"));

        tempCsvFile.delete();
    }

    @Test
    void 시간이_빠진_잘못된_형식에_대해_예외를_발생시킨다() throws Exception {
        String csvData = "nickname,datetime\n에드,2025-02-13\n";
        File tempCsvFile = createTempCsvFile(csvData);

        AttendanceHistoryLoader loader = new AttendanceHistoryLoader();
        FileReader reader = new FileReader(tempCsvFile);

        assertThrows(RuntimeException.class, () -> loader.loadCrews(reader));

        tempCsvFile.delete();
    }
}
