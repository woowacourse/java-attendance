import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.AttendanceBook;
import domain.AttendanceHistoryLoader;
import domain.Crew;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class PenaltyCheckTest {

    @Test
    void 제적_위험자인_크루의_정보를_가져온다() throws IOException {
        AttendanceBook attendanceBook = new AttendanceBook();
        AttendanceHistoryLoader attendanceHistoryLoader = new AttendanceHistoryLoader();
        attendanceBook = attendanceHistoryLoader.initializeAttendanceWith(
                new FileReader("src/main/resources/attendances.csv"));
        attendanceBook.recordAllAbsences();

        final var penaltyCrews = attendanceBook.getPenaltyHistory();

        assertTrue(penaltyCrews.containsKey(new Crew("짱수")));
        assertTrue(penaltyCrews.containsKey(new Crew("빙봉")));
        assertTrue(penaltyCrews.containsKey(new Crew("빙티")));
        assertFalse(penaltyCrews.containsKey(new Crew("이든")));
    }
}