import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.AttendanceBook;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AttandanceHistoryLoaderTest {
    private File createTempFile(String csvData) throws IOException {
        Path tempFile = Files.createTempFile("tempAttendances", ".csv");
        Files.write(tempFile, csvData.getBytes());

        return tempFile.toFile();
    }

    @Test
    void CSV_파일을_읽어_출석부를_초기화한다() throws IOException {
        String csvData = """
                nickname,datetime
                에드, 2025-02-26 10:03
                제프, 2025-02-26 10:01
                """;
        File file = createTempFile(csvData);
        AttandanceHistoryLoader loader = new AttandanceHistoryLoader();
        AttendanceBook attendanceBook = loader.initializeAttendanceWith(file);

        assertThat(attendanceBook.getAttendances("에드")).isNotInstanceOf(Exception.class);
        assertThat(attendanceBook.getAttendances("제프")).isNotInstanceOf(Exception.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            """
                    nickname,datetime
                    에드, 2025-02-26-10:03
                    제프, 2025-02-26 10:01
                    """,
            """
                    nickname,datetime
                    에드, 2025-02-26
                    제프, 2025-02-26 10:01
                    """,
            """               
                    nickname,datetime
                    에드, 2025-02-26 10:03
                    제프, 10:01 025-02-26
                    """})
    void CSV_파일의_형식이_잘못되면_예외를_발생시킨다(String csvData) throws IOException {
        File file = createTempFile(csvData);
        AttandanceHistoryLoader loader = new AttandanceHistoryLoader();

        assertThatThrownBy(() -> loader.initializeAttendanceWith(file))
                .isInstanceOf(IOException.class)
                .hasMessageStartingWith("[ERROR]");
    }

}