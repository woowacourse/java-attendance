package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import except.AttendanceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import strategy.AttendanceCurrentDateGenerateStrategy;

public class AttendanceReaderTest {

    @Test
    @DisplayName("파일로부터 출석 입력")
    void addAttendanceFromFile() {
        String fileName = "/testAttendance.csv";
        AttendanceReader attendanceReader = new AttendanceReader(fileName);
        CrewAttendances crewAttendances = new CrewAttendances(new AttendanceCurrentDateGenerateStrategy(),
                attendanceReader.readAttendances());
        CrewAttendanceHistories crewAttendanceHistories = crewAttendances.crewAttendancesHistory("투다");
        assertThat(crewAttendanceHistories.crewAttendanceHistories().size())
                .isEqualTo(5);
    }

    @Test
    @DisplayName("유효하지 않은 파일로부터 출석 입력시 예외가 발생한다")
    void invalidAddAttendanceFromFile() {
        String fileName = "/invalidTestAttendance.csv";
        AttendanceReader attendanceReader = new AttendanceReader(fileName);
        CrewAttendances crewAttendances = new CrewAttendances(new AttendanceCurrentDateGenerateStrategy(),
                attendanceReader.readAttendances());
        CrewAttendanceHistories crewAttendanceHistories = crewAttendances.crewAttendancesHistory("투다");
        assertThatThrownBy(crewAttendanceHistories::crewAttendanceHistories)
                .isInstanceOf(AttendanceException.class);
    }
}
