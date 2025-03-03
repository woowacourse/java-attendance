package util.loader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Scanner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileLoaderTest {

    @Test
    @DisplayName("리소스에서 파일을 읽어올 수 있다.")
    void successLoadFile() {
        Scanner scanner = FileLoader.loadCSV("src/test/resources/attendances.csv");
        assertThat(scanner).isInstanceOf(Scanner.class);
    }

    @Test
    @DisplayName("리소스의 파일명을 잘못입력하면 읽어올 수 없다.")
    void failedLoadFile() {
        assertThatThrownBy(() -> FileLoader.loadCSV("src/test/resources/attendance.csv"));
    }
}