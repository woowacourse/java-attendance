import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.Attendance;
import domain.AttendanceBook;
import domain.Day;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class AttendanceHistoryReadTest {
    AttendanceBook attendanceBook;

    void setUpAttendances(String nickname) {
        attendanceBook = new AttendanceBook();
        // 출석 2회, 지각 3회, 결석 1회, 기록 없음 2회
        Attendance attendance1 = new Attendance(new Day(LocalDate.of(2025, 2, 17)), LocalTime.of(13, 5)); // 출석
        Attendance attendance2 = new Attendance(new Day(LocalDate.of(2025, 2, 18)), LocalTime.of(10, 15)); // 지각
        Attendance attendance3 = new Attendance(new Day(LocalDate.of(2025, 2, 19)), LocalTime.of(11, 0)); // 결석
        Attendance attendance4 = new Attendance(new Day(LocalDate.of(2025, 2, 20)), LocalTime.of(9, 50)); // 출석
        Attendance attendance5 = new Attendance(new Day(LocalDate.of(2025, 2, 24)), LocalTime.of(13, 6)); // 지각
        Attendance attendance6 = new Attendance(new Day(LocalDate.of(2025, 2, 26)), LocalTime.of(10, 25)); // 지각
        attendanceBook.recordAttendance(nickname, attendance1);
        attendanceBook.recordAttendance(nickname, attendance2);
        attendanceBook.recordAttendance(nickname, attendance3);
        attendanceBook.recordAttendance(nickname, attendance4);
        attendanceBook.recordAttendance(nickname, attendance5);
        attendanceBook.recordAttendance(nickname, attendance6);
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
    void 닉네임에_따라_각_출석_상태_횟수를_확인한다() {
        final var nickname = "에드";
        setUpAttendances(nickname);

        AttendanceHistoryRead attendanceHistoryRead = new AttendanceHistoryRead();
        final var lateCount = attendanceHistoryRead.getLateCountOf(attendanceBook, nickname);
        final var absentCount = attendanceHistoryRead.getAbsentCountOf(attendanceBook, nickname);
        final var attendanceCount = attendanceHistoryRead.getAttendanceCountOf(attendanceBook, nickname);

        assertEquals(3, lateCount);
        assertEquals(1, absentCount);
        assertEquals(2, attendanceCount);
    }

    @Test
    void 등교를_하지_않은_날은_결석으로_처리한다() {
        final var nickname = "에드";
        setUpAttendances(nickname);

        AttendanceHistoryRead attendanceHistoryRead = new AttendanceHistoryRead();
        attendanceHistoryRead.recordAllAbsence(attendanceBook);
        final var absentCount = attendanceHistoryRead.getAbsentCountOf(attendanceBook, nickname);
        assertEquals(3, absentCount);
    }

}