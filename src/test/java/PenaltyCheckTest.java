import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.AttendanceBook;
import domain.AttendanceHistoryLoader;
import domain.Crew;
import java.io.FileReader;
import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;

class PenaltyCheckTest {

    @Test
    void 제적_위험자인_크루의_정보를_가져온다() throws IOException {
        Clock testClock = Clock.fixed(Instant.parse("2025-02-28T10:00:00Z"), ZoneId.of("Asia/Seoul"));
        AttendanceHistoryLoader attendanceHistoryLoader = new AttendanceHistoryLoader();
        AttendanceBook attendanceBook = attendanceHistoryLoader.initializeAttendanceWith(
                new FileReader("src/main/resources/attendances.csv"));
        attendanceBook.recordAllAbsences(testClock);

        final var penaltyCrews = attendanceBook.getPenaltyHistory(testClock);

        assertTrue(penaltyCrews.containsKey(new Crew("짱수")));
        assertTrue(penaltyCrews.containsKey(new Crew("빙봉")));
        assertTrue(penaltyCrews.containsKey(new Crew("빙티")));
        assertFalse(penaltyCrews.containsKey(new Crew("이든")));
    }
}