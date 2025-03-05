package util;

import domain.AttendanceBook;
import domain.Crew;
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
        Map<Crew, AttendanceBook> initialAttendances = AttendanceFileReader.read(ATTENDANCE_FILE_PATH);
        CrewsAttendanceBook crewsAttendanceBook = new CrewsAttendanceBook(initialAttendances);
        Crew crew = crewsAttendanceBook.getCrewByName("쿠키");
        AttendanceBook cookieAttendanceBook = crewsAttendanceBook.getAttendances().get(crew);

        // then
        Assertions.assertThat(cookieAttendanceBook.getAttendanceBookHistory().size()).isEqualTo(10);
    }
}
