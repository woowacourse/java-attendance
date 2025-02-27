package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import strategy.TestAttendanceCurrentDateGenerateStrategy;

public class CrewDismissHistoryTest {

    private static CrewAttendances crewAttendances;
    private static TestAttendanceCurrentDateGenerateStrategy testAttendanceCurrentDateGenerateStrategy;

    @BeforeEach
    void initiate() {
        testAttendanceCurrentDateGenerateStrategy = new TestAttendanceCurrentDateGenerateStrategy(
                LocalDate.of(2024, 12, 2));
        crewAttendances = new CrewAttendances(testAttendanceCurrentDateGenerateStrategy);
        crewAttendances.addAttendance("투다", LocalTime.of(8, 3));
        testAttendanceCurrentDateGenerateStrategy.setTestDate(LocalDate.of(2024, 12, 3));
        crewAttendances.addAttendance("투다", LocalTime.of(8, 3));
        testAttendanceCurrentDateGenerateStrategy.setTestDate(LocalDate.of(2024, 12, 4));
        crewAttendances.addAttendance("투다", LocalTime.of(8, 3));
        testAttendanceCurrentDateGenerateStrategy.setTestDate(LocalDate.of(2024, 12, 5));
        crewAttendances.addAttendance("투다", LocalTime.of(8, 3));
        testAttendanceCurrentDateGenerateStrategy.setTestDate(LocalDate.of(2024, 12, 6));
    }

    @Test
    @DisplayName("제적대상자 테스트")
    void crewDismissTest() {
        testAttendanceCurrentDateGenerateStrategy.setTestDate(LocalDate.of(2024, 12, 10));
        crewAttendances.addAttendance("투다", LocalTime.of(13, 50));
        testAttendanceCurrentDateGenerateStrategy.setTestDate(LocalDate.of(2024, 12, 11));
        crewAttendances.addAttendance("투다", LocalTime.of(13, 50));
        testAttendanceCurrentDateGenerateStrategy.setTestDate(LocalDate.of(2024, 12, 12));
        crewAttendances.addAttendance("투다", LocalTime.of(13, 50));

        Map<AttendanceStatus, Integer> attendanceStatuses = new HashMap<>();
        attendanceStatuses.put(AttendanceStatus.ABSENCE, 4);
        attendanceStatuses.put(AttendanceStatus.ATTENDANCE, 4);
        CrewDismiss crewDismiss = new CrewDismiss(attendanceStatuses);
        assertThat(crewAttendances.orderedDismissHistory().get(0)
                .crewDismiss().dismissStatus())
                .isEqualTo(new CrewDismiss(attendanceStatuses).dismissStatus());
    }

    @Test
    @DisplayName("지각 3번이면 결석으로 간주된다")
    void crewDismissLateTest() {
        testAttendanceCurrentDateGenerateStrategy.setTestDate(LocalDate.of(2024, 12, 9));
        crewAttendances.addAttendance("투다", LocalTime.of(13, 6));
        testAttendanceCurrentDateGenerateStrategy.setTestDate(LocalDate.of(2024, 12, 10));
        crewAttendances.addAttendance("투다", LocalTime.of(10, 7));
        testAttendanceCurrentDateGenerateStrategy.setTestDate(LocalDate.of(2024, 12, 11));
        crewAttendances.addAttendance("투다", LocalTime.of(10, 6));
        testAttendanceCurrentDateGenerateStrategy.setTestDate(LocalDate.of(2024, 12, 12));
        crewAttendances.addAttendance("투다", LocalTime.of(10, 6));
        testAttendanceCurrentDateGenerateStrategy.setTestDate(LocalDate.of(2024, 12, 13));
        crewAttendances.addAttendance("투다", LocalTime.of(10, 6));
        testAttendanceCurrentDateGenerateStrategy.setTestDate(LocalDate.of(2024, 12, 16));
        crewAttendances.addAttendance("투다", LocalTime.of(13, 6));
        testAttendanceCurrentDateGenerateStrategy.setTestDate(LocalDate.of(2024, 12, 17));

        Map<AttendanceStatus, Integer> attendanceStatuses = new HashMap<>();
        attendanceStatuses.put(AttendanceStatus.ABSENCE, 3);
        attendanceStatuses.put(AttendanceStatus.ATTENDANCE, 4);
        CrewDismiss crewDismiss = new CrewDismiss(attendanceStatuses);

        assertThat(crewAttendances.orderedDismissHistory().get(0)
                .crewDismiss()
                .dismissStatus())
                .isEqualTo(crewDismiss.dismissStatus());
    }

    @Test
    @DisplayName("제적이 아닐시 출력하지 않는다")
    void nonDismissCrewTest() {
        List<CrewDismissHistory> orderDismissHistories = crewAttendances.orderedDismissHistory();
        assertThat(orderDismissHistories.size())
                .isEqualTo(0);
    }
}
