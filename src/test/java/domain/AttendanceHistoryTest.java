package domain;

import static org.assertj.core.api.Assertions.assertThatIterable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import strategy.TestAttendanceCurrentDateGenerateStrategy;

public class AttendanceHistoryTest {

    @Test
    @DisplayName("닉네임을 입력하여 전날까지의 출석 기록을 확인할 수 있다.")
    void attendanceHistoryTest() {
        String nickname = "투다";
        TestAttendanceCurrentDateGenerateStrategy testAttendanceCurrentDateGenerateStrategy = new TestAttendanceCurrentDateGenerateStrategy(
                LocalDate.of(2024, 12, 2));
        CrewAttendances crewAttendances = new CrewAttendances(testAttendanceCurrentDateGenerateStrategy);
        crewAttendances.addAttendance("투다", LocalTime.of(8, 3));
        testAttendanceCurrentDateGenerateStrategy.setTestDate(LocalDate.of(2024, 12, 3));
        crewAttendances.addAttendance("투다", LocalTime.of(8, 3));
        LocalDate notIncludeDate = LocalDate.of(2024, 12, 4);
        testAttendanceCurrentDateGenerateStrategy.setTestDate(notIncludeDate);
        crewAttendances.addAttendance("투다", LocalTime.of(8, 3));
        AttendanceDate attendanceDate1 = new AttendanceDate(LocalDate.of(2024, 12, 2));
        CrewAttendance crewAttendance = new CrewAttendance(new AttendanceTime(LocalTime.of(8, 3), attendanceDate1));
        AttendanceDate attendanceDate2 = new AttendanceDate(LocalDate.of(2024, 12, 3));
        CrewAttendance crewAttendance2 = new CrewAttendance(new AttendanceTime(LocalTime.of(8, 3), attendanceDate2));
        List<CrewAttendanceHistory> expectCrewAttendanceHistories = List.of(
                new CrewAttendanceHistory(crewAttendance, attendanceDate1),
                new CrewAttendanceHistory(crewAttendance2, attendanceDate2));
        testAttendanceCurrentDateGenerateStrategy.setTestDate(notIncludeDate);
        assertThatIterable(crewAttendances.crewAttendancesHistory(nickname))
                .containsExactlyInAnyOrderElementsOf(expectCrewAttendanceHistories);
    }
}
