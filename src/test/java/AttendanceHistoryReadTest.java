import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.AttendanceBook;
import domain.AttendanceHistoryLoader;
import domain.Attendances;
import domain.Penalty;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class AttendanceHistoryReadTest {
    AttendanceBook attendanceBook;

    void setUpAttendances() throws IOException {
        AttendanceHistoryLoader attendanceHistoryLoader = new AttendanceHistoryLoader();
        attendanceBook = attendanceHistoryLoader.initializeAttendanceWith(
                new FileReader("src/main/resources/attendances.csv"));
    }

    @Test
    void 닉네임에_따라_각_출석_상태_횟수를_확인한다() throws IOException {
        setUpAttendances();

        final var nickname = "짱수";
        Attendances attendances = attendanceBook.getAttendances(nickname);
        final var lateCount = attendances.getLateCount();
        final var absentCount = attendances.getAbsentCount();
        final var attendanceCount = attendances.getTotalCount() - lateCount - absentCount;

        assertEquals(1, lateCount);
        assertEquals(2, absentCount);
        assertEquals(12, attendanceCount);
    }

    @Test
    void 등교를_하지_않은_날은_결석으로_처리한다() throws IOException {
        final var nickname = "짱수";
        setUpAttendances();

        attendanceBook.recordAllAbsences();
        Attendances attendances = attendanceBook.getAttendances(nickname);

        final var absentCount = attendances.getAbsentCount();
        assertEquals(6, absentCount);
    }

    @Test
    void 지각_3회를_결석_1회로_간주하여_어떤_제제의_대상자인지_확인한다() throws IOException {
        final var nickname = "짱수";
        setUpAttendances();

        attendanceBook.recordAllAbsences();
        Attendances attendances = attendanceBook.getAttendances(nickname);

        final var penaltyStatus = attendances.getPenaltyStatus();
        final var expected = Penalty.EXPULSION;
        assertEquals(expected, penaltyStatus);

    }

}