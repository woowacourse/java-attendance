package util;

import domain.AttendanceBook;
import domain.CrewsAttendanceBook;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttendanceFileReaderTest {
    @Test
    void 쿠키_출석부를_가져올_수_있다() {
        // given
        String ATTENDANCE_FILE_PATH = "src/test/resources/attendances_test.csv";

        // when
        Map<String, AttendanceBook> initialAttendances = AttendanceFileReader.read(ATTENDANCE_FILE_PATH);
        CrewsAttendanceBook crewsAttendanceBook = new CrewsAttendanceBook(initialAttendances);
        AttendanceBook cookieAttendanceBook = crewsAttendanceBook.getAttendances().get("쿠키");

        // then
        Assertions.assertThat(cookieAttendanceBook.getAttendanceBook().size()).isEqualTo(8);
    }
}
