package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import attendance.exception.AttendanceFileException;
import attendance.utility.CsvReader;

public class CsvReaderTest {

    @Test
    @DisplayName("잘못된 파일 주소가 있을 경우, 예외를 발생한다.")
    void error_wrongFileURL() {
        var repository = new CsvReader("/invalid");

        assertThatThrownBy(repository::getLines)
            .isInstanceOf(AttendanceFileException.class)
            .hasMessageContaining("존재하지 않은 파일입니다.");
    }

    @Test
    @DisplayName("유효하지 않은 파일일 경우, 예외를 발생한다.")
    void error_invalidFile() {
        var repository = new CsvReader("");

        assertThatThrownBy(repository::getLines)
            .isInstanceOf(AttendanceFileException.class)
            .hasMessageContaining("유효하지 않은 파일입니다.");
    }

}
