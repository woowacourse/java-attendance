import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.AttendanceBook;
import domain.AttendanceHistoryLoader;
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
    //닉네임을 입력하면 전날까지의 크루 출석 기록을 확인할 수 있다.
    //출석 기록 확인시 출석, 지각, 결석의 각 횟수를 확인할 수 있다.
    //출석 기록 확인시 어떤 제제의 대상자인지 확인할 수 있다.
    //등교하지 않아 출석 기록이 없는 날은 결석으로 간주한다.
    //누적 지각 및 결석 횟수에 따라 경고 또는 면담, 제적을 시행한다.

    //  지각 3회는 결석 1회로 간주한다.

    //  경고 대상자: 결석 2회 이상
    //  면담 대상자: 결석 3회 이상
    //  제적 대상자: 결석 5회 초과
    @Test
    void 닉네임에_따라_각_출석_상태_횟수를_확인한다() throws IOException {
        setUpAttendances();

        final var nickname = "짱수";
        AttendanceHistoryRead attendanceHistoryRead = new AttendanceHistoryRead();
        final var lateCount = attendanceHistoryRead.getLateCountOf(attendanceBook, nickname);
        final var absentCount = attendanceHistoryRead.getAbsentCountOf(attendanceBook, nickname);
        final var attendanceCount = attendanceHistoryRead.getAttendanceCountOf(attendanceBook, nickname);

        assertEquals(1, lateCount);
        assertEquals(2, absentCount);
        assertEquals(12, attendanceCount);
    }

    @Test
    void 등교를_하지_않은_날은_결석으로_처리한다() throws IOException {
        final var nickname = "짱수";
        setUpAttendances();

        AttendanceHistoryRead attendanceHistoryRead = new AttendanceHistoryRead();
        attendanceHistoryRead.recordAllAbsence(attendanceBook);
        final var absentCount = attendanceHistoryRead.getAbsentCountOf(attendanceBook, nickname);
        assertEquals(5, absentCount);
    }

}