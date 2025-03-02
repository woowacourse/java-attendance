package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewStatisticsTest {
    @DisplayName("정상: 크루들 통계 생성의 정상 작동 확인")
    @Test
    void successExecutionCreateCrewStatistics() {
        CrewNames crewNames = new CrewNames();
        crewNames.initializeCrewNames(Set.of("초코", "치즈"));

        Attendances attendances = new Attendances(
                List.of(
                        new Attendance(
                                new CrewName("초코"),
                                new AttendanceDate(LocalDate.parse("2025-02-27")),
                                new AttendanceTime("09:07")
                        ),
                        new Attendance(
                                new CrewName("치즈"),
                                new AttendanceDate(LocalDate.parse("2025-02-26")),
                                new AttendanceTime("10:00")
                        )
                )
        );

        CrewStatistics crewStatistics = new CrewStatistics();
        crewStatistics.generateCrewStatistics(crewNames, attendances);

        assertThat(crewStatistics.getCrewStatistics()).hasSize(2);
    }
}
