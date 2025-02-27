import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.AttendanceBook;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class AttandanceHistoryLoaderTest {
    private File createTempFile(String csvData) throws IOException {
        Path tempFile = Files.createTempFile("tempAttendances", ".csv");
        Files.write(tempFile, csvData.getBytes());

        return tempFile.toFile();
    }

    @Test
    void CSV_파일을_읽어_출석부를_초기화한다() throws IOException {
        String csvData = """
                에드, 2025-02-26 10:03
                제프, 2025-02-26 10:01
                """;
        File file = createTempFile(csvData);
        AttandanceHistoryLoader loader = new AttandanceHistoryLoader();
        AttendanceBook attendanceBook = loader.initializeAttendanceWith(file);

        assertThat(attendanceBook.getAttendances("에드")).isNotInstanceOf(Exception.class);
        assertThat(attendanceBook.getAttendances("제프")).isNotInstanceOf(Exception.class);


    }

}