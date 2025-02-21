package function;

import static constants.TestTimeMaker.MONDAY_ATTEND;

import domain.AttendanceBook;
import domain.Crew;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class CheckPenaltyCrewTest {

    @Test
    void 전날까지의_크루_출석_기록을_바탕으로_제적_위험자를_파악한다() {
        AttendanceBook attendanceBook = new AttendanceBook();

        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 2), MONDAY_ATTEND));
        attendanceBook.addNewCrew(crew1);

        Crew crew2 = Crew.createByName("우유");
        crew2.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 2), MONDAY_ATTEND));
        attendanceBook.addNewCrew(crew2);

        // TODO: 제적 위험자와 제적 위험자가 아닌 크루로 예시를 다시 작성할 것!

        attendanceBook.checkPenaltyCrew();
    }
}
