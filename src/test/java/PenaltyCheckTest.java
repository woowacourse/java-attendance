import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.AttendanceBook;
import domain.AttendanceHistoryLoader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class PenaltyCheckTest {

    @Test
    void 제적_위험자인_크루의_정보를_가져온다() throws IOException {
        PenaltyCheck penaltyCheck = new PenaltyCheck();

        AttendanceBook attendanceBook = new AttendanceBook();
        AttendanceHistoryLoader attendanceHistoryLoader = new AttendanceHistoryLoader();
        attendanceBook = attendanceHistoryLoader.initializeAttendanceWith(
                new FileReader("src/main/resources/attendances.csv"));
        attendanceBook.recordAllAbsences();

        final var penaltyCrews = penaltyCheck.getPenaltyHistory(attendanceBook);

        assertTrue(penaltyCrews.containsKey("짱수"));
        assertTrue(penaltyCrews.containsKey("빙봉"));
        assertTrue(penaltyCrews.containsKey("빙티"));
        assertFalse(penaltyCrews.containsKey("이든"));
    }
}