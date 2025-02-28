package domain;

import static domain.AttendanceStatus.ABSENT_THRESHOLD_MINUTES;
import static domain.AttendanceStatus.EXCEPT_MONDAY_ATTEND_TIME;
import static domain.AttendanceStatus.LATE_THRESHOLD_MINUTES;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {

    private final String testCrewName = "pobi";
    private AttendanceBook attendanceBook;


    @BeforeEach
    void setUp() {
        attendanceBook = new AttendanceBook();
        attendanceBook.addCrewByName(testCrewName);
    }

    @Test
    @DisplayName("출석 확인을 수정하려면 수정하려는 날짜, 등교 시간을 입력하여 기록을 수정할 수 있다.")
    void attendanceBookTest1() {
        LocalDate date = LocalDate.now();
        LocalTime originalTime = LocalTime.now();
        LocalTime timeToModify = LocalTime.now().minusHours(1);

        attendanceBook.putAttendanceRecordByName(testCrewName, date, originalTime);
        attendanceBook.modifyAttendanceRecordByName(testCrewName, date, timeToModify);

        assertThat(attendanceBook.findTimeByNameAndDate(testCrewName, date))
                .isEqualTo(timeToModify);
    }

    @Test
    @DisplayName("출석, 지각, 결석 횟수를 확인할 수 있다.")
    void attendanceBookTest2() {
        attendanceBook.putAttendanceRecordByName(testCrewName, LocalDate.of(2024, 12, 3),
                EXCEPT_MONDAY_ATTEND_TIME);
        attendanceBook.putAttendanceRecordByName(testCrewName, LocalDate.of(2024, 12, 4),
                EXCEPT_MONDAY_ATTEND_TIME.plusMinutes(LATE_THRESHOLD_MINUTES + 1));
        attendanceBook.putAttendanceRecordByName(testCrewName, LocalDate.of(2024, 12, 5),
                EXCEPT_MONDAY_ATTEND_TIME.plusMinutes(ABSENT_THRESHOLD_MINUTES + 1));

        assertThat(attendanceBook.getAttendanceStatusCountByName(testCrewName, AttendanceStatus.ATTEND))
                .isEqualTo(1);
        assertThat(attendanceBook.getAttendanceStatusCountByName(testCrewName, AttendanceStatus.LATE))
                .isEqualTo(1);
        assertThat(attendanceBook.getAttendanceStatusCountByName(testCrewName, AttendanceStatus.ABSENT))
                .isEqualTo(29);
    }
}
