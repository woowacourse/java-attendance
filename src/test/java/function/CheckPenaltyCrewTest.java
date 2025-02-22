package function;

import static constants.NumberConstants.END_DAY_OF_DECEMBER;
import static constants.NumberConstants.START_DAY_OF_DECEMBER;
import static constants.TestTimeMaker.EXCEPT_MONDAY_ATTEND;
import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendanceBook;
import domain.Calendar;
import domain.PenaltyStatus;
import dto.CrewPenaltyResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CheckPenaltyCrewTest {
    private AttendanceBook attendanceBook;

    @BeforeEach
    void setup() {
        attendanceBook = new AttendanceBook();

        for (int day = START_DAY_OF_DECEMBER; day <= END_DAY_OF_DECEMBER; day++) {
            if (!Calendar.checkIsWorkingDay(day)) {
                continue;
            }

            if (day > 3) { // 2(월) 3(화) 결석
                attendanceBook.initialize("우유", Map.of(LocalDate.of(2024, 12, day), EXCEPT_MONDAY_ATTEND));
            }
            if (day > 4) { // 2(월) 3(화) 4(수) 결석
                attendanceBook.initialize("초코", Map.of(LocalDate.of(2024, 12, day), EXCEPT_MONDAY_ATTEND));
            }
            if (day > 9) { // 2(월) 3(화) 4(수) 5(목) 6(금) 9(월) 결석
                attendanceBook.initialize("쿠키", Map.of(LocalDate.of(2024, 12, day), EXCEPT_MONDAY_ATTEND));
            }
        }
        // 우유 2회 결석, 초코 3회 결석, 쿠키 5회 결석
    }

    @Test
    @DisplayName("전날까지의_크루_출석_기록을_바탕으로_제적_위험자를_파악한다")
    void 전날까지의_크루_출석_기록을_바탕으로_제적_위험자를_파악한다() {
        List<CrewPenaltyResponse> penaltyCrews = attendanceBook.checkPenaltyCrew();

        assertThat(penaltyCrews.getFirst().penaltyStatus()).isEqualTo(PenaltyStatus.WARNING);
        assertThat(penaltyCrews.get(1).penaltyStatus()).isEqualTo(PenaltyStatus.INTERVIEW);
        assertThat(penaltyCrews.getLast().penaltyStatus()).isEqualTo(PenaltyStatus.EXPULSION);
    }
}
