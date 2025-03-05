package attendance.model;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("출석 기록 파일 테스트")
class AttendancesFileTest {

    private static final String TEST_FILE_PATH = "src/test/java/attendance/model/test-attendance.csv";

    @BeforeEach
    void setup() {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(TEST_FILE_PATH))) {
            writer.write("");
        } catch (IOException e) {
            throw new IllegalStateException("테스트 파일을 생성 중 문제가 발생했습니다. (경로: %s)".formatted(TEST_FILE_PATH), e);
        }
    }

    @AfterAll
    static void afterAll() {
        try {
            Files.deleteIfExists(Path.of(TEST_FILE_PATH));
        } catch (IOException e) {
            throw new IllegalStateException("테스트 파일 삭제 중 문제가 발생했습니다. (경로: %s)".formatted(TEST_FILE_PATH), e);
        }
    }

    @DisplayName("정상적인 파일인 경우 예외가 발생하지 않는다.")
    @Test
    void shouldNotThrowException_WhenValidFile() {
        // given
        overwriteTestFile("""
                nickname,datetime
                쿠키,2024-12-13 10:08
                빙봉,2024-12-13 10:07
                """);
        AttendancesFile attendancesFile = new AttendancesFile();

        // when & then
        assertThatCode(() -> attendancesFile.load(TEST_FILE_PATH))
                .doesNotThrowAnyException();
    }

    @DisplayName("정상적인 파일인 경우 출석 로그 목록이 잘 만들어지고, 중복된 출석을 추가할 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenValidFileAndExistAttendanceLogAdd() {
        // given
        overwriteTestFile("""
                nickname,datetime
                쿠키,2024-12-13 10:08
                """);
        AttendancesFile attendancesFile = new AttendancesFile();
        AttendanceLog attendanceLog = new AttendanceLog(
                new Nickname("쿠키"),
                LocalDate.of(2024, 12, 13),
                LocalTime.of(10, 8)
        );
        AttendanceLogs attendanceLogs = attendancesFile.load(TEST_FILE_PATH);

        // when & then
        assertThatCode(() -> attendanceLogs.add(attendanceLog))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("금일 출석 기록이 존재하여 추가되지 않았습니다. 수정이 필요한 경우 출석 수정 기능을 사용해주세요.");
    }

    @DisplayName("파일 입력에 문제가 발생한 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenInvalidFileInput() {
        // given
        String invalidFilePath = "////////////";
        AttendancesFile attendancesFile = new AttendancesFile();

        // when & then
        assertThatCode(() -> attendancesFile.load(invalidFilePath))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("출석 로그 파일 입출력 중 문제가 발생했습니다. (경로: %s)".formatted(invalidFilePath));
    }

    @DisplayName("정상적인 파일인 경우 출석 로그 목록이 잘 만들어지고, 새로운 출석을 추가할 경우 예외가 발생하지 않는다.")
    @Test
    void shouldNotThrowException_WhenValidFileAndNewAttendanceLogAdd() {
        // given
        overwriteTestFile("""
                nickname,datetime
                쿠키,2024-12-13 10:08
                """);
        AttendancesFile attendancesFile = new AttendancesFile();
        AttendanceLog newAttendanceLog = new AttendanceLog(
                new Nickname("벨로"),
                LocalDate.of(2024, 12, 13),
                LocalTime.of(10, 8)
        );
        AttendanceLogs attendanceLogs = attendancesFile.load(TEST_FILE_PATH);

        // when & then
        assertThatCode(() -> attendanceLogs.add(newAttendanceLog))
                .doesNotThrowAnyException();
    }

    @DisplayName("파일에 헤더가 올바르지 않은 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenInvalidHeaderFile() {
        // given
        overwriteTestFile("""
                name,timestamp
                쿠키,2024-12-13 10:08
                빙봉,2024-12-13 10:07
                """);
        AttendancesFile attendancesFile = new AttendancesFile();

        // when & then
        assertThatCode(() -> attendancesFile.load(TEST_FILE_PATH))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("출석 로그 파일의 헤더 형식이 올바르지 않습니다. (헤더: name,timestamp)");
    }

    @DisplayName("파일이 비어있는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenEmptyFile() {
        // given
        overwriteTestFile("");
        AttendancesFile attendancesFile = new AttendancesFile();

        // when & then
        assertThatCode(() -> attendancesFile.load(TEST_FILE_PATH))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("출석 로그 파일이 파일이 비어 있습니다. (경로: %s)".formatted(TEST_FILE_PATH));
    }

    @DisplayName("파일 데이터가 잘못된 형식인 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenInvalidFormatFile() {
        // given
        overwriteTestFile("""
                nickname,datetime
                쿠키 | 2024-12-13 10:08
                """);
        AttendancesFile attendancesFile = new AttendancesFile();

        // when & then
        assertThatCode(() -> attendancesFile.load(TEST_FILE_PATH))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("출석 로그 파일의 데이터 형식이 올바르지 않습니다. (입력된 값: %s)".formatted("쿠키 | 2024-12-13 10:08"));
    }

    @DisplayName("파일 데이터가 잘못된 형식인 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenInvalidDateTimeFormatFile() {
        // given
        overwriteTestFile("""
                nickname,datetime
                쿠키,2024년12월13 10시08분
                """);
        AttendancesFile attendancesFile = new AttendancesFile();

        // when & then
        assertThatCode(() -> attendancesFile.load(TEST_FILE_PATH))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("출석 로그 파일의 시간 데이터 형식이 올바르지 않습니다. (입력된 값: %s)".formatted("2024년12월13 10시08분"));
    }

    private void overwriteTestFile(String content) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(TEST_FILE_PATH))) {
            writer.write(content);
        } catch (IOException e) {
            throw new IllegalStateException("테스트 파일 초기화 중 문제가 발생했습니다. 경로: %s".formatted(TEST_FILE_PATH), e);
        }
    }
}
