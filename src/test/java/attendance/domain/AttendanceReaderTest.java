package attendance.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceReaderTest {
    private final SystemDateTime systemDateTime = new AttendanceDateTime();
    private static final String ATTENDANCE_CSV = "attendances.csv";

    @Test
    @DisplayName("csv 정보를 불러온다.")
    void test_LoadingCsvWithNoException() {
        var attendanceReader = new AttendanceReader(ATTENDANCE_CSV, systemDateTime);

        assertDoesNotThrow(attendanceReader::load);
    }

    @Test
    @DisplayName("csv 정보를 불러와, AttendanceBook에 저장하여 반환한다.")
    void test_returnAttendanceBookFromCsv() throws FileNotFoundException {
        var attendanceReader = new AttendanceReader(ATTENDANCE_CSV, systemDateTime);
        var loadedBook = attendanceReader.load();
        var attendanceBook = loadedBook.attendancesBook();
        assertAll(
            () -> assertThat(attendanceBook.keySet().size()).isEqualTo(5),
            () -> assertThat(attendanceBook.containsKey(new Nickname("짱수"))).isTrue()
        );
    }

    @Test
    @DisplayName("잘못된 주소의 csv 정보를 불러올때, 예외가 발생한다.")
    void error_LoadingCsvWithWrongFileName() {
        var attendanceReader = new AttendanceReader("/ErrorFile.csv", systemDateTime);

        Assertions.assertThatThrownBy(attendanceReader::load)
            .isInstanceOf(FileNotFoundException.class)
            .hasMessageContaining("파일을 찾을 수 없습니다")
            .hasMessageContaining("ErrorFile");
    }

}
