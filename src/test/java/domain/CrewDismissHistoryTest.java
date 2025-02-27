package domain;

import static org.assertj.core.api.Assertions.assertThatIterable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import strategy.TestAttendanceCurrentDateGenerateStrategy;

public class CrewDismissHistoryTest {

    private static CrewAttendances crewAttendances;
    private static TestAttendanceCurrentDateGenerateStrategy testAttendanceCurrentDateGenerateStrategy;

    @Test
    @DisplayName("제적대상자 테스트")
    void crewDismissTest() {
        Map<AttendanceStatus, Integer> map = new HashMap<>();
        map.put(AttendanceStatus.ABSENCE, 3);
        map.put(AttendanceStatus.LATE, 3);
        List<CrewDismissHistory> crewDismissHistories = new ArrayList<>();
        CrewDismissHistory first = new CrewDismissHistory("투다", new CrewDismiss(map));
        CrewDismissHistory second = new CrewDismissHistory("빙티", new CrewDismiss(map));
        crewDismissHistories.add(first);
        map.put(AttendanceStatus.LATE, 0);
        crewDismissHistories.add(second);
        List<CrewDismissHistory> orderDismissHistories = List.of(first, second);
        assertThatIterable(orderDismissHistories)
                .containsExactlyElementsOf(orderDismissHistories);
    }
}
