package domain;

import static domain.AttendanceStatus.ABSENT_THRESHOLD_MINUTES;
import static domain.AttendanceStatus.EXCEPT_MONDAY_ATTEND_TIME;
import static domain.AttendanceStatus.LATE_THRESHOLD_MINUTES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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
                .isEqualTo(14);
    }

    @Test
    @DisplayName("존재하지 않는 크루의 출석 기록을 조회하면 예외가 발생한다.")
    void attendanceBookTest3() {
        LocalDate date = LocalDate.of(2024, 12, 1);

        assertThatThrownBy(() -> attendanceBook.findTimeByNameAndDate("없는크루", date))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.CREW_NAME_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("출석 기록이 없는 날짜의 출석 기록을 수정하면 예외가 발생한다.")
    void attendanceBookTest4() {
        LocalDate date = LocalDate.of(2024, 12, 10);
        LocalTime timeToModify = LocalTime.of(9, 30);

        assertThatThrownBy(() -> attendanceBook.modifyAttendanceRecordByName(testCrewName, date, timeToModify))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.ATTENDANCE_DATE_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("출석 기록이 없는 크루는 결석으로 계산된다.")
    void attendanceBookTest5() {
        assertThat(attendanceBook.getAttendanceStatusCountByName(testCrewName, AttendanceStatus.ABSENT))
                .isEqualTo(16);
    }

    @Test
    @DisplayName("12월 한 달 동안의 출석 기록을 반영하여 벌점이 있는 크루만 반환한다.")
    void attendanceBookTest6() {
        String noPenaltyCrew = "성실";
        attendanceBook.addCrewByName(noPenaltyCrew);
        for (int day = 1; day <= 31; day++) {
            attendanceBook.putAttendanceRecordByName(noPenaltyCrew, LocalDate.of(2024, 12, day),
                    EXCEPT_MONDAY_ATTEND_TIME);
        }

        String penaltyCrew = "불량";
        attendanceBook.addCrewByName(penaltyCrew);
        for (int day = 1; day <= 31; day++) {
            LocalTime attendanceTime;
            if (day % 5 == 0) {
                attendanceTime = EXCEPT_MONDAY_ATTEND_TIME.plusMinutes(ABSENT_THRESHOLD_MINUTES + 1);
            } else if (day % 3 == 0) {
                attendanceTime = EXCEPT_MONDAY_ATTEND_TIME.plusMinutes(LATE_THRESHOLD_MINUTES + 1);
            } else {
                attendanceTime = EXCEPT_MONDAY_ATTEND_TIME;
            }
            attendanceBook.putAttendanceRecordByName(penaltyCrew, LocalDate.of(2024, 12, day), attendanceTime);
        }

        List<Crew> penaltyCrews = attendanceBook.findCrewsWithPenalty();

        assertThat(penaltyCrews).extracting(Crew::getName)
                .contains(penaltyCrew)
                .doesNotContain(noPenaltyCrew);
    }

}
