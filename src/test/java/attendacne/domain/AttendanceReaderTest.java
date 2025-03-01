package attendacne.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import attendance.domain.AttendanceReader;

public class AttendanceReaderTest {

    private static final String ATTENDANCE_CSV = "/attendances.csv";

    @Test
    @DisplayName("csv 정보를 불러온다.")
    void test_LoadingCsvWithNoException() {
        var attendanceReader = new AttendanceReader(ATTENDANCE_CSV);

        assertDoesNotThrow(attendanceReader::load);
    }

    @Test
    @DisplayName("잘못된 주소의 csv 정보를 불러올때, 예외가 발생한다.")
    void error_LoadingCsvWithWrongFileName() {
        var attendanceReader = new AttendanceReader("/ErrorFile.csv");

        Assertions.assertThatThrownBy(attendanceReader::load)
            .isInstanceOf(FileNotFoundException.class)
            .hasMessageContaining("파일을 찾을 수 없습니다")
            .hasMessageContaining("ErrorFile");
    }

    @Test
    @DisplayName("파일을 읽어올 수 없을 경우, 예외가 발생한다.")
    void error_CantLoadingFile() {
        var attendanceReader = new AttendanceReader("ErrorFile");

        Assertions.assertThatThrownBy(attendanceReader::load)
            .isInstanceOf(FileNotFoundException.class)
            .hasMessageContaining("파일을 읽을 수 없습니다");
    }
}
