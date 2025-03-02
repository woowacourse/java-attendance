import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendanceBook;
import domain.AttendanceHistoryLoader;
import domain.Attendances;
import domain.Crew;
import domain.Penalty;
import java.io.FileReader;
import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;

class AttendanceHistoryReadTest {
    AttendanceBook attendanceBook;
    Clock testClock = Clock.fixed(Instant.parse("2025-02-28T10:00:00Z"), ZoneId.of("Asia/Seoul"));

    void setUpAttendances() throws IOException {
        AttendanceHistoryLoader attendanceHistoryLoader = new AttendanceHistoryLoader();
        attendanceBook = attendanceHistoryLoader.initializeAttendanceWith(
                new FileReader("src/main/resources/attendances.csv"));
    }

    @Test
    void 닉네임에_따라_각_출석_상태_횟수를_확인한다() throws IOException {
        setUpAttendances();

        Crew crew = new Crew("짱수");
        Attendances attendances = attendanceBook.getAttendances(crew);
        Integer lateCount = attendances.getLateCount(testClock);
        Integer absentCount = attendances.getAbsentCount(testClock);
        Integer attendanceCount = attendances.getTotalCount() - lateCount - absentCount;

        assertThat(lateCount).isEqualTo(1);
        assertThat(absentCount).isEqualTo(2);
        assertThat(attendanceCount).isEqualTo(12);
    }

    @Test
    void 등교를_하지_않은_날은_결석으로_처리한다() throws IOException {
        Crew crew = new Crew("짱수");
        setUpAttendances();

        attendanceBook.recordAllAbsences(testClock);
        Attendances attendances = attendanceBook.getAttendances(crew);

        Integer absentCount = attendances.getAbsentCount(testClock);
        assertThat(absentCount).isEqualTo(6);
    }

    @Test
    void 지각_3회를_결석_1회로_간주하여_어떤_제제의_대상자인지_확인한다() throws IOException {
        Crew crew = new Crew("짱수");
        setUpAttendances();

        attendanceBook.recordAllAbsences(testClock);
        Attendances attendances = attendanceBook.getAttendances(crew);

        Penalty penaltyStatus = attendances.getPenaltyStatus(testClock);
        Penalty expected = Penalty.EXPULSION;
        assertThat(penaltyStatus).isEqualTo(expected);

    }

}