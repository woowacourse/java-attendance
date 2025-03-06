package reader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reader.exception.FileReadException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AttendanceFileReaderTest {

    @Test
    @DisplayName("올바른 파일 경로를 제공받으면, 출석 데이터를 읽을 수 있다.")
    void canReadFileFromValidPath() {
        // given
        AttendanceFileReader attendanceFileReader = new AttendanceFileReader();

        // when
        // then
        assertThatCode(() -> attendanceFileReader.read(AttendanceFileReader.ATTENDANCE_FILE_PATH))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("잘못된 파일 경로를 제공받으면, 예외가 발생한다.")
    void cannotReadFileFromInvalidPath() {
        // given
        AttendanceFileReader attendanceFileReader = new AttendanceFileReader();

        // when
        // then
        assertThatThrownBy(() -> attendanceFileReader.read("invalid/path/file.csv"))
                .isInstanceOf(FileReadException.class)
                .hasMessageContaining("출석 데이터를 읽어오는데 실패했습니다");
    }
}
