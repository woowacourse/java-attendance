package attendacne.domain;

import static org.junit.jupiter.api.Assertions.*;

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
}
