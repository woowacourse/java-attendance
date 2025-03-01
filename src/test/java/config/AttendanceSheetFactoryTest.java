package config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Attendance;
import domain.AttendanceSheet;
import domain.policy.AbsentPolicy;
import domain.policy.TimePolicy;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class AttendanceSheetFactoryTest {
    ReadFile<Attendance, AttendanceSheet> readFile;

    @BeforeEach
    void setUp() {
        readFile = new AttendanceSheetFactory(new TimePolicy(), new AbsentPolicy());
    }

    @Test
    @DisplayName("지정된 위치의 파일이 아니면 예외가 발생한다")
    public void validateFileReaderPolicyTest() {
        assertThatThrownBy(() -> readFile.loadFile(Paths.get("attendances.csv")))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("쉼표에 따라 나눈 문자열의 개수가 차이가 나면 예외가 발생한다")
    public void validateFileFormatTest(@TempDir Path tempDir) throws IOException {
        //given
        Path path = tempDir.resolve("attendances.csv");
        List<String> lines = List.of("nickname,date,time", "링크,2024-12-13, 10:08");
        Files.write(path, lines);

        //when-then
        assertThatThrownBy(() -> readFile.loadFile(path))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("날짜와 시간 형식이 맞지 않으면 예외가 발생한다")
    public void validateAttendanceDateTimeFormatTest(@TempDir Path tempDir) throws IOException {
        //given
        Path path = tempDir.resolve("attendances.csv");
        List<String> lines = List.of("nickname,dateTime", "링크,2024:12:13 09:11");
        Files.write(path, lines);

        //when-then
        assertThatThrownBy(() -> readFile.loadFile(path))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("파일 전체를 읽어들여 출석부에 기록할 수 있다")
    public void createAttendancesTest(@TempDir Path tempDir) throws IOException {
        //given
        Path path = tempDir.resolve("attendances.csv");
        List<String> lines = List.of("nickname,dateTime", "링크,2024-12-12 09:11", "링크,2024-12-13 09:11");
        Files.write(path, lines);

        //when-then
        assertThat(readFile.loadFile(path))
                .isInstanceOf(AttendanceSheet.class);
    }
}
