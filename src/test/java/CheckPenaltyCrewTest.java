import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.AttendanceBook;
import domain.Crew;
import domain.PenaltyStatus;
import dto.CrewPenaltyResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class CheckPenaltyCrewTest {

    @Test
    void 전날까지의_크루_출석_기록을_바탕으로_제적_위험자를_파악한다() {
        AttendanceBook attendanceBook = new AttendanceBook();

        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 6)));
        attendanceBook.addNewCrew(crew1);

        Crew crew2 = Crew.createByName("우유");
        crew2.addDailyAttendance(Map.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 7)));
        attendanceBook.addNewCrew(crew2);

        List<CrewPenaltyResponse> responses = attendanceBook.checkPenaltyCrew();
        List<CrewPenaltyResponse> expectedResponses = List.of(
                new CrewPenaltyResponse("쿠키", 19, 1, PenaltyStatus.EXPULSION),
                new CrewPenaltyResponse("우유", 19, 1, PenaltyStatus.EXPULSION)
        );

        assertThat(responses).isEqualTo(expectedResponses);
    }
}
