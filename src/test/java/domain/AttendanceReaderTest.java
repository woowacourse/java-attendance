package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import except.AttendanceException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import strategy.AttendanceCurrentDateGenerateStrategy;
import strategy.TestAttendanceCurrentDateGenerateStrategy;

public class AttendanceReaderTest {

    @Test
    @DisplayName("파일로부터 출석 입력")
    void addAttendanceFromFile() {
        String fileName = "/testAttendance.csv";
        AttendanceReader attendanceReader = new AttendanceReader(fileName);
        CrewAttendances crewAttendances = new CrewAttendances(
                new TestAttendanceCurrentDateGenerateStrategy(LocalDate.of(2024, 12, 7)),
                attendanceReader.readAttendances());

        SystemTimeCrewAttendanceHistories systemTimeCrewAttendanceHistories = crewAttendances.crewAttendancesHistory(
                "투다");
        assertThat(systemTimeCrewAttendanceHistories.renewDateCrewAttendance().size())
                .isEqualTo(5);
    }

    @Test
    @DisplayName("유효하지 않은 파일로부터 출석 입력시 예외가 발생한다")
    void invalidAddAttendanceFromFile() {
        String fileName = "/invalidTestAttendance.csv";
        AttendanceReader attendanceReader = new AttendanceReader(fileName);

        assertThatThrownBy(() -> new CrewAttendances(new AttendanceCurrentDateGenerateStrategy(),
                attendanceReader.readAttendances()))
                .isInstanceOf(AttendanceException.class);
    }
}
