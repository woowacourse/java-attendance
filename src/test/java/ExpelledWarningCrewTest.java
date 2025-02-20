import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.AttendanceManager;
import domain.StatisticsResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ExpelledWarningCrewTest {

    AttendanceManager attendanceManager = new AttendanceManager();
    List<LocalDateTime> testRecords1 = new ArrayList<>();
    List<LocalDateTime> testRecords2 = new ArrayList<>();

    @BeforeEach
    void setUp() {
        testRecords1 = List.of(
            LocalDateTime.of(2024, 12, 2, 13, 6), // 지각
            LocalDateTime.of(2024, 12, 3, 9, 7), // 지각
            LocalDateTime.of(2024, 12, 4, 10, 8), // 지각
            LocalDateTime.of(2024, 12, 5, 10, 9),// 지각
            LocalDateTime.of(2024, 12, 6, 10, 10),// 지각
            LocalDateTime.of(2024, 12, 9, 13, 40),// 결석
            LocalDateTime.of(2024, 12, 10, 10, 40) // 결석
        );

        testRecords2 = List.of(
            LocalDateTime.of(2024, 12, 2, 13, 8), // 지각
            LocalDateTime.of(2024, 12, 3, 10, 5), // 지각
            LocalDateTime.of(2024, 12, 4, 10, 6), // 지각
            LocalDateTime.of(2024, 12, 5, 10, 7),// 지각
            LocalDateTime.of(2024, 12, 6, 10, 6),// 지각
            LocalDateTime.of(2024, 12, 9, 13, 6),// 지각
            LocalDateTime.of(2024, 12, 10, 10, 40) // 결석
        );

        attendanceManager.createCrew("이든", testRecords1); // 면담 대상자
        attendanceManager.createCrew("빙봉", testRecords2); // 면담 대상자
    }

    @DisplayName("크루 출석 기록을 바탕으로 제적 위험자를 파악한다.")
    @Test
    void 제적_위험자_계산() {
        LocalDate nowDate = LocalDate.of(2024, 12, 10);
        Map<String, StatisticsResult> warningCrews = attendanceManager.findWarningCrews(nowDate);

        assertThat(warningCrews.size()).isEqualTo(2);
    }
}
