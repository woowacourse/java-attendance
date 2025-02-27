package domain;

import static domain.AttendanceStatus.ABSENT_THRESHOLD_MINUTES;
import static domain.AttendanceStatus.EXCEPT_MONDAY_ATTEND_TIME;
import static domain.AttendanceStatus.LATE_THRESHOLD_MINUTES;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import dto.PenaltyCountResponse;
import java.time.LocalDate;
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
    @DisplayName("출석, 지각, 결석 횟수를 종합하여 확인할 수 있다.")
    void attendanceBookTest1() {
        assertThat(attendanceBook.getPenaltyCountResponseByName(testCrewName))
                .isEqualTo(new PenaltyCountResponse(0, 0, 31));

        Crew testCrew = attendanceBook.findCrewByName(testCrewName);
        testCrew.checkAttendance(LocalDate.of(2024, 12, 3), EXCEPT_MONDAY_ATTEND_TIME);
        testCrew.checkAttendance(LocalDate.of(2024, 12, 4),
                EXCEPT_MONDAY_ATTEND_TIME.plusMinutes(LATE_THRESHOLD_MINUTES + 1));
        testCrew.checkAttendance(LocalDate.of(2024, 12, 5),
                EXCEPT_MONDAY_ATTEND_TIME.plusMinutes(ABSENT_THRESHOLD_MINUTES + 1));

        assertThat(attendanceBook.getPenaltyCountResponseByName(testCrewName))
                .isEqualTo(new PenaltyCountResponse(1, 1, 29));
    }
}