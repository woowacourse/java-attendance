package function;

import static domain.policy.TimePolicy.OPERATING_START;
import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendanceBook;
import domain.policy.DatePolicy;
import domain.policy.PenaltyPolicy;
import dto.PenaltyCrewResponse;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CheckPenaltyCrewTest {
    private AttendanceBook attendanceBook;

    @BeforeEach
    void SetUp() {
        // given
        attendanceBook = new AttendanceBook();
        for (int day = 1; day <= 31; day++) {
            if (DatePolicy.isNotTrainingDay(LocalDate.of(2024, 12, day))) {
                continue;
            }

            if (day > 3) { // 2(월) 3(화) 결석
                attendanceBook.registerCrew("키위", LocalDate.of(2024, 12, day),
                        OPERATING_START.getTime().plusMinutes(1));
            }
            if (day > 4) { // 2(월) 3(화) 4(수) 결석
                attendanceBook.registerCrew("딸기", LocalDate.of(2024, 12, day),
                        OPERATING_START.getTime().plusMinutes(1));
            }
            if (day > 9) { // 2(월) 3(화) 4(수) 5(목) 6(금) 9(월) 결석
                attendanceBook.registerCrew("쿠키", LocalDate.of(2024, 12, day),
                        OPERATING_START.getTime().plusMinutes(1));
            }
        }
        // 키위 2회 결석, 딸기 3회 결석, 쿠키 5회 결석
    }

    @Test
    @DisplayName("전날까지의 크루 출석 기록을 바탕으로 제적 위험자를 파악한다.")
    void Using_Crew_Attendance_Records_To_Checking_Penalty_Crews() {
        List<PenaltyCrewResponse> responses = attendanceBook.checkPenaltyCrew();

        assertThat(responses.getFirst().penalty()).isEqualTo(PenaltyPolicy.EXPULSION.getPenalty());
        assertThat(responses.get(1).penalty()).isEqualTo(PenaltyPolicy.INTERVIEW.getPenalty());
        assertThat(responses.getLast().penalty()).isEqualTo(PenaltyPolicy.WARNING.getPenalty());
    }
}